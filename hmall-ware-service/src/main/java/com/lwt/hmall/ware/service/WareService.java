package com.lwt.hmall.ware.service;

import com.lwt.hmall.api.bean.OmsOrderItem;
import com.lwt.hmall.api.bean.WmsWareSku;
import com.lwt.hmall.redis.util.RedisLock;
import com.lwt.hmall.service.config.redis.cache.CacheFuzzyRemove;
import com.lwt.hmall.ware.constant.CacheName;
import com.lwt.hmall.ware.mapper.WmsWareInfoMapper;
import com.lwt.hmall.ware.mapper.WmsWareSkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/4 17:54
 * @Description
 */
@Service
@CacheConfig(cacheNames = CacheName.CACHE_NAME)
public class WareService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private WmsWareInfoMapper wmsWareInfoMapper;
    @Autowired
    private WmsWareSkuMapper wmsWareSkuMapper;

    /**
     * 获取可用sku的库存总量
     * @param skuId
     * @return
     */
    @Cacheable
    public int getAvailableWareSkuStockBySkuId(long skuId){
        WmsWareSku wmsWareSkuParam = new WmsWareSku();
        wmsWareSkuParam.setSkuId(skuId);
        List<WmsWareSku> wmsWareSkus = wmsWareSkuMapper.select(wmsWareSkuParam);
        int stock=0;
        for (WmsWareSku wareSku : wmsWareSkus) {
            stock+=wareSku.getStock();
            stock-=wareSku.getStockLocked();
        }
        return stock;
    }

    /**
     * 锁定库存
     * @param omsOrderItems
     * @return
     */
    @CacheFuzzyRemove(cacheName = CacheName.CACHE_NAME,value = {
            "#targetName+'get*'",
            "#targetName+'list*'"
    })
    @Transactional
    public boolean lockWareSkuByOrderItems(List<OmsOrderItem> omsOrderItems){
        RedisLock redisLock = new RedisLock(redisTemplate);
        try {
            redisLock.lock(10);

            //拆单
            for (OmsOrderItem omsOrderItem : omsOrderItems) {
                long productQuantity = omsOrderItem.getProductQuantity();

                Example example = new Example(WmsWareSku.class);
                example.createCriteria().andEqualTo("skuId", omsOrderItem.getProductSkuId())
                        .andGreaterThan("stock", 0);
                List<WmsWareSku> wmsWareSkus = wmsWareSkuMapper.selectByExample(example);
                for (WmsWareSku wareSku : wmsWareSkus) {
                    long q = wareSku.getStock() - wareSku.getStockLocked();
                    //锁定库存
                    if (productQuantity <= q) {
                        wareSku.setStockLocked(wareSku.getStockLocked() + productQuantity);
                        productQuantity = 0;
                        wmsWareSkuMapper.updateByPrimaryKeySelective(wareSku);
                        break;
                    } else {
                        wareSku.setStockLocked(wareSku.getStock());
                        productQuantity -= q;
                        wmsWareSkuMapper.updateByPrimaryKeySelective(wareSku);
                    }
                }
                //库存不足回滚
                if (productQuantity != 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return false;
                }
            }
            return true;
        }finally {
            redisLock.unlock();
        }
    }

    /**
     * 解锁库存
     * @param omsOrderItems
     * @return
     */
    @CacheFuzzyRemove(cacheName = CacheName.CACHE_NAME,value = {
            "#targetName+'get*'",
            "#targetName+'list*'"
    })
    @Transactional
    public boolean unlockWareSkuByOrderItems(List<OmsOrderItem> omsOrderItems){
        RedisLock redisLock = new RedisLock(redisTemplate);
        try {
            redisLock.lock(10);

            for (OmsOrderItem omsOrderItem : omsOrderItems) {
                long productQuantity = omsOrderItem.getProductQuantity();

                Example example = new Example(WmsWareSku.class);
                example.createCriteria().andEqualTo("skuId", omsOrderItem.getProductSkuId())
                        .andGreaterThan("stockLocked", 0);
                List<WmsWareSku> wmsWareSkus = wmsWareSkuMapper.selectByExample(example);
                for (WmsWareSku wareSku : wmsWareSkus) {
                    long q = wareSku.getStockLocked();
                    //解锁库存
                    if (productQuantity <= q) {
                        wareSku.setStockLocked(wareSku.getStockLocked() - productQuantity);
                        productQuantity = 0;
                        wmsWareSkuMapper.updateByPrimaryKeySelective(wareSku);
                        break;
                    } else {
                        wareSku.setStockLocked(0L);
                        productQuantity -= q;
                        wmsWareSkuMapper.updateByPrimaryKeySelective(wareSku);
                    }
                }
                //锁定不足回滚
                if (productQuantity != 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return false;
                }
            }
            return true;
        }finally {
            redisLock.unlock();
        }
    }

}
