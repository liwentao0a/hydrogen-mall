package com.lwt.hmall.order.service;

import com.lwt.hmall.api.bean.OmsCartItem;
import com.lwt.hmall.api.bean.PmsSkuInfo;
import com.lwt.hmall.api.bean.PmsSkuSaleAttrValue;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.common.client.SkuClient;
import com.lwt.hmall.order.constant.CacheName;
import com.lwt.hmall.order.mapper.OmsCartItemMapper;
import com.lwt.hmall.redis.cache.autoconfigure.CacheFuzzyRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @Author lwt
 * @Date 2020/3/5 11:58
 * @Description
 */
@Service
@CacheConfig(cacheNames = CacheName.CACHE_NAME)
public class CartService {

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private OmsCartItemMapper omsCartItemMapper;

    /**
     * 保存sku到购物车
     * @param skuId
     * @param quantity
     * @param userId
     * @param userNickname
     * @return
     */
    @CacheFuzzyRemove(cacheName = CacheName.CACHE_NAME,value = {
            "#targetName+'get*'",
            "#targetName+'list*'"
    })
    public boolean saveCartItem(long skuId,long quantity,long userId,String userNickname){
        //判断购物车里是否已有商品
        OmsCartItem omsCartItem = getCartItemBySkuIdAndUserId(skuId, userId);
        if (omsCartItem!=null){
            omsCartItem.setQuantity(omsCartItem.getQuantity()+quantity);
            return omsCartItemMapper.updateByPrimaryKey(omsCartItem)>0;
        }

        //获取商品
        Result<PmsSkuInfo> skuResult = skuClient.getSkuBySkuId(skuId);
        if (skuResult.getCode()!= CodeEnum.SUCCESS.getCode()) {
            return false;
        }
        PmsSkuInfo skuData = skuResult.getData();

        //保存购物车商品
        OmsCartItem omsCartItemParam = new OmsCartItem();
        omsCartItemParam.setProductId(skuData.getProductId());
        omsCartItemParam.setProductSkuId(skuData.getId());
        Timestamp createDate = new Timestamp(System.currentTimeMillis());
        omsCartItemParam.setCreateDate(createDate);
        omsCartItemParam.setModifyDate(createDate);
        omsCartItemParam.setPrice(skuData.getPrice());
        omsCartItemParam.setProductCategoryId(skuData.getCatalog3Id());
        omsCartItemParam.setProductName(skuData.getName());
        omsCartItemParam.setProductSubTitle(skuData.getDescription());
        omsCartItemParam.setQuantity(quantity);
        omsCartItemParam.setProductPic(skuData.getDefaultImg());
        omsCartItemParam.setProductSn(UUID.randomUUID().toString()+"-"+createDate.toString());
        omsCartItemParam.setUserId(userId);
        omsCartItemParam.setUserNickname(userNickname);
        omsCartItemParam.setDeleteStatus(0L);
        List<PmsSkuSaleAttrValue> skuSaleAttrValues = skuData.getSkuSaleAttrValues();
        StringBuffer skuSaleAttrValuesJson=new StringBuffer();
        skuSaleAttrValuesJson.append("[");
        for (PmsSkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValues) {
            skuSaleAttrValuesJson.append("{\"key\":\"");
            skuSaleAttrValuesJson.append(skuSaleAttrValue.getSaleAttrName());
            skuSaleAttrValuesJson.append("\",\"value\":\"");
            skuSaleAttrValuesJson.append(skuSaleAttrValue.getSaleAttrValueName());
            skuSaleAttrValuesJson.append("\"}");
            skuSaleAttrValuesJson.append(",");
        }
        skuSaleAttrValuesJson.delete(skuSaleAttrValuesJson.length()-1,skuSaleAttrValuesJson.length());
        skuSaleAttrValuesJson.append("]");
        omsCartItemParam.setProductAttr(skuSaleAttrValuesJson.toString());
        return omsCartItemMapper.insertSelective(omsCartItemParam)>0;
    }

    /**
     * 获取用户购物车列表
     * @param userId
     * @return
     */
    @Cacheable
    public List<OmsCartItem> listCartItemsByUserId(long userId){
        OmsCartItem omsCartItemParam = new OmsCartItem();
        omsCartItemParam.setUserId(userId);
        List<OmsCartItem> omsCartItems = omsCartItemMapper.select(omsCartItemParam);
        return omsCartItems;
    }

    /**
     * 删除购物车商品
     * @param cartItemId
     * @param userId
     * @return
     */
    @CacheFuzzyRemove(cacheName = CacheName.CACHE_NAME,value = {
            "#targetName+'get*'",
            "#targetName+'list*'"
    })
    public boolean removeCartItemByCartItemIdAndUserId(long cartItemId,long userId){
        OmsCartItem omsCartItemParam = new OmsCartItem();
        omsCartItemParam.setId(cartItemId);
        omsCartItemParam.setUserId(userId);
        return omsCartItemMapper.delete(omsCartItemParam)>0;
    }

    /**
     * 获取购物车商品信息
     * @param cartItemIds
     * @return
     */
    @Cacheable
    public List<OmsCartItem> listCartItemsInCartItemIds(List<Long> cartItemIds){
        Example example = new Example(OmsCartItem.class);
        example.createCriteria().andIn("id",cartItemIds);
        List<OmsCartItem> omsCartItems = omsCartItemMapper.selectByExample(example);
        return omsCartItems;
    }

    /**
     * 获取购物车商品信息
     * @param skuId
     * @param userId
     * @return
     */
    @Cacheable
    public OmsCartItem getCartItemBySkuIdAndUserId(long skuId,long userId){
        OmsCartItem omsCartItemParam = new OmsCartItem();
        omsCartItemParam.setProductSkuId(skuId);
        omsCartItemParam.setUserId(userId);
        OmsCartItem omsCartItem = omsCartItemMapper.selectOne(omsCartItemParam);
        return omsCartItem;
    }

    /**
     * 删除购物车商品
     * @param cartItemIds
     * @param userId
     * @return
     */
    @CacheFuzzyRemove(cacheName = CacheName.CACHE_NAME,value = {
            "#targetName+'get*'",
            "#targetName+'list*'"
    })
    public boolean removeCartItemByUserIdAndInCartItemIds(List<Long> cartItemIds,long userId){
        Example example = new Example(OmsCartItem.class);
        example.createCriteria().andIn("id",cartItemIds).andEqualTo("userId",userId);
        return omsCartItemMapper.deleteByExample(example)>0;
    }
}
