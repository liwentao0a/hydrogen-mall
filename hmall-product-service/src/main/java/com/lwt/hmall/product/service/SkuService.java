package com.lwt.hmall.product.service;

import com.lwt.hmall.api.bean.*;
import com.lwt.hmall.api.util.PageUtils;
import com.lwt.hmall.product.constant.CacheName;
import com.lwt.hmall.product.mapper.PmsSkuAttrValueMapper;
import com.lwt.hmall.product.mapper.PmsSkuImageMapper;
import com.lwt.hmall.product.mapper.PmsSkuInfoMapper;
import com.lwt.hmall.product.mapper.PmsSkuSaleAttrValueMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lwt
 * @Date 2020/2/26 10:58
 * @Description
 */
@Service
@CacheConfig(cacheNames = CacheName.CACHE_NAME)
public class SkuService {

    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    /**
     * 分页查找skuInfo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Cacheable
    @Transactional
    public Page<PmsSkuInfo> pageSkuInfos(int pageNum, int pageSize){
        Page<PmsSkuInfo> pmsSkuInfoPage = pageSkuInfosByExample(null, pageNum, pageSize);
        return pmsSkuInfoPage;
    }

    /**
     * 分页查找skuInfo
     * @param example
     * @param pageNum
     * @param pageSize
     * @return
     */
    private Page<PmsSkuInfo> pageSkuInfosByExample(Example example,int pageNum,int pageSize){
        int total = pmsSkuInfoMapper.selectCountByExample(example);
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectByExampleAndRowBounds(example,new RowBounds((pageNum-1)*pageSize,pageSize));
        Page<PmsSkuInfo> pmsSkuInfoPage = PageUtils.page(pmsSkuInfos, total, pageNum, pageSize);
        return pmsSkuInfoPage;
    }

    /**
     * 分页查找skuInfo
     * @param catalog3
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Cacheable
    @Transactional
    public Page<PmsSkuInfo> pageSkuInfosByCatalog3(Long catalog3,int pageNum,int pageSize){
        Example example = null;
        if (catalog3!=null) {
            example=new Example(PmsSkuInfo.class);
            example.createCriteria().andEqualTo("catalog3Id", catalog3);
        }
        Page<PmsSkuInfo> pmsSkuInfoPage = pageSkuInfosByExample(example, pageNum, pageSize);
        return pmsSkuInfoPage;
    }

    /**
     * 分页查找skuInfo
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Cacheable
    @Transactional
    public Page<PmsSkuInfo> pageSkuInfosByKeyword(String keyword,int pageNum,int pageSize){
        Example example = new Example(PmsSkuInfo.class);
        example.createCriteria()
                .andLike("name","%"+keyword+"%")
                .orLike("description","%"+keyword+"%");
        Page<PmsSkuInfo> pmsSkuInfoPage = pageSkuInfosByExample(example, pageNum, pageSize);
        return pmsSkuInfoPage;
    }

    /**
     * 获取sku相关的所有信息(info,image,saleAttr,Attr)
     * @param productId
     * @return
     */
    @Cacheable
    public List<PmsSkuInfo> listSkusByProductId(long productId){
        //获取skuInfo
        List<PmsSkuInfo> pmsSkuInfos = listSkuInfosByProductId(productId);

        //将查询的skuId组成集合
        List<Long> skuIdParams=new ArrayList<>();
        Map<Long,PmsSkuInfo> pmsSkuInfoMap=new HashMap<>();
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            skuIdParams.add(pmsSkuInfo.getId());
            pmsSkuInfoMap.put(pmsSkuInfo.getId(),pmsSkuInfo);
        }

        //获取skuImage
        List<PmsSkuImage> pmsSkuImages = listSkuImagesInSkuIds(skuIdParams);
        for (PmsSkuImage pmsSkuImage : pmsSkuImages) {
            Long skuId = pmsSkuImage.getSkuId();
            PmsSkuInfo pmsSkuInfo = pmsSkuInfoMap.get(skuId);
            List<PmsSkuImage> skuImages = pmsSkuInfo.getSkuImages();
            if (skuImages==null){
                skuImages=new ArrayList<>();
                pmsSkuInfo.setSkuImages(skuImages);
            }
            skuImages.add(pmsSkuImage);
        }

        //获取skuSaleAttrValue
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = listSkuSaleAttrValuesInSkuIds(skuIdParams);
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValues) {
            Long skuId = pmsSkuSaleAttrValue.getSkuId();
            PmsSkuInfo pmsSkuInfo = pmsSkuInfoMap.get(skuId);
            List<PmsSkuSaleAttrValue> skuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValues();
            if (skuSaleAttrValues==null){
                skuSaleAttrValues=new ArrayList<>();
                pmsSkuInfo.setSkuSaleAttrValues(skuSaleAttrValues);
            }
            skuSaleAttrValues.add(pmsSkuSaleAttrValue);
        }

        //获取skuAttrValue
        List<PmsSkuAttrValue> pmsSkuAttrValues = listSkuAttrValuesInSkuIds(skuIdParams);
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValues) {
            Long skuId = pmsSkuAttrValue.getSkuId();
            PmsSkuInfo pmsSkuInfo = pmsSkuInfoMap.get(skuId);
            List<PmsSkuAttrValue> skuAttrValues = pmsSkuInfo.getSkuAttrValues();
            if (skuAttrValues==null){
                skuAttrValues=new ArrayList<>();
                pmsSkuInfo.setSkuAttrValues(skuAttrValues);
            }
            skuAttrValues.add(pmsSkuAttrValue);
        }

        return pmsSkuInfos;
    }

    /**
     * 获取skuAttrValue信息
     * @param skuIdParams
     * @return
     */
    @Cacheable
    public List<PmsSkuAttrValue> listSkuAttrValuesInSkuIds(List<Long> skuIdParams) {
        Example pmsSkuAttrValueExample = new Example(PmsSkuAttrValue.class);
        pmsSkuAttrValueExample.createCriteria().andIn("skuId",skuIdParams);
        return pmsSkuAttrValueMapper.selectByExample(pmsSkuAttrValueExample);
    }

    /**
     * 获取skuSaleAttrValue信息
     * @param skuIdParams
     * @return
     */
    @Cacheable
    public List<PmsSkuSaleAttrValue> listSkuSaleAttrValuesInSkuIds(List<Long> skuIdParams) {
        Example pmsSkuSaleAttrValueExample = new Example(PmsSkuSaleAttrValue.class);
        pmsSkuSaleAttrValueExample.createCriteria().andIn("skuId",skuIdParams);
        return pmsSkuSaleAttrValueMapper.selectByExample(pmsSkuSaleAttrValueExample);
    }

    /**
     * 获取skuImage信息
     * @param skuIdParams
     * @return
     */
    @Cacheable
    public List<PmsSkuImage> listSkuImagesInSkuIds(List<Long> skuIdParams) {
        Example pmsSkuImageExample = new Example(PmsSkuImage.class);
        pmsSkuImageExample.createCriteria().andIn("skuId",skuIdParams);
        return pmsSkuImageMapper.selectByExample(pmsSkuImageExample);
    }

    /**
     * 获取skuInfo信息
     * @param productId
     * @return
     */
    @Cacheable
    public List<PmsSkuInfo> listSkuInfosByProductId(long productId) {
        PmsSkuInfo pmsSkuInfoParam = new PmsSkuInfo();
        pmsSkuInfoParam.setProductId(productId);
        return pmsSkuInfoMapper.select(pmsSkuInfoParam);
    }

    /**
     * 获取sku信息
     * @param skuId
     * @return
     */
    @Cacheable
    public PmsSkuInfo getSkuBySkuId(long skuId){
        PmsSkuInfo pmsSkuInfo = getSkuInfoBySkuId(skuId);

        PmsSkuImage pmsSkuImageParam = new PmsSkuImage();
        pmsSkuImageParam.setSkuId(skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImageParam);
        pmsSkuInfo.setSkuImages(pmsSkuImages);

        PmsSkuSaleAttrValue pmsSkuSaleAttrValueParam = new PmsSkuSaleAttrValue();
        pmsSkuSaleAttrValueParam.setSkuId(skuId);
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuSaleAttrValueMapper.select(pmsSkuSaleAttrValueParam);
        pmsSkuInfo.setSkuSaleAttrValues(pmsSkuSaleAttrValues);

        PmsSkuAttrValue pmsSkuAttrValueParam = new PmsSkuAttrValue();
        pmsSkuAttrValueParam.setSkuId(skuId);
        List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuAttrValueMapper.select(pmsSkuAttrValueParam);
        pmsSkuInfo.setSkuAttrValues(pmsSkuAttrValues);

        return pmsSkuInfo;
    }

    /**
     * 获取skuInfo信息
     * @param skuId
     * @return
     */
    @Cacheable
    public PmsSkuInfo getSkuInfoBySkuId(long skuId) {
        PmsSkuInfo pmsSkuInfoParam = new PmsSkuInfo();
        pmsSkuInfoParam.setId(skuId);
        return pmsSkuInfoMapper.selectOne(pmsSkuInfoParam);
    }

    /**
     * 获取sku价格
     * @param skuId
     * @return
     */
    @Cacheable
    public BigDecimal getSkuPriceBySkuId(long skuId){
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);
        return pmsSkuInfo==null?new BigDecimal(-1):pmsSkuInfo.getPrice();
    }
}
