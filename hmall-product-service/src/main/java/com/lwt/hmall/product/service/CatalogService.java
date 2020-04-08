package com.lwt.hmall.product.service;

import com.lwt.hmall.api.bean.PmsBaseCatalog1;
import com.lwt.hmall.product.constant.CacheName;
import com.lwt.hmall.product.mapper.PmsBaseCatalog1Mapper;
import com.lwt.hmall.product.mapper.PmsBaseCatalog2Mapper;
import com.lwt.hmall.product.mapper.PmsBaseCatalog3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/2/26 10:59
 * @Description
 */
@Service
@CacheConfig(cacheNames = CacheName.CACHE_NAME)
public class CatalogService {

    @Autowired
    private PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    @Autowired
    private PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    @Autowired
    private PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    /**
     * 查询1、2、3级分类信息
     * @return
     */
    @Cacheable
    public List<PmsBaseCatalog1> listCatalogs(){
        return pmsBaseCatalog1Mapper.selectAllCatalog();
    }
}
