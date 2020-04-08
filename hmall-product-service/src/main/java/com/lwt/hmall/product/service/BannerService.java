package com.lwt.hmall.product.service;

import com.lwt.hmall.api.bean.PmsBaseBanner;
import com.lwt.hmall.product.constant.CacheName;
import com.lwt.hmall.product.mapper.PmsBaseBannerMapper;
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
public class BannerService {

    @Autowired
    private PmsBaseBannerMapper pmsBaseBannerMapper;

    /**
     * 获取所有轮播图信息
     * @return
     */
    @Cacheable
    public List<PmsBaseBanner> listBaseBanners(){
        return pmsBaseBannerMapper.selectAll();
    }
}
