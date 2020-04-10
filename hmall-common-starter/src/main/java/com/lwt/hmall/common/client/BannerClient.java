package com.lwt.hmall.common.client;

import com.lwt.hmall.api.bean.PmsBaseBanner;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.common.client.fallback.BannerFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "hmall-product-service",contextId = "BannerClient",fallbackFactory = BannerFallbackFactory.class)
public interface BannerClient {

    /**
     * 获取所有轮播图信息
     * @return
     */
    @RequestMapping(value = "/listBaseBanners",method = RequestMethod.GET)
    public Result<List<PmsBaseBanner>> listBaseBanners();

}
