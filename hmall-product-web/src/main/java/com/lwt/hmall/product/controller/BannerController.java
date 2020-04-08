package com.lwt.hmall.product.controller;

import com.lwt.hmall.api.bean.PmsBaseBanner;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.common.client.BannerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/2/26 20:04
 * @Description
 */
@RestController
@Validated
public class BannerController {

    @Autowired
    private BannerClient bannerClient;

    /**
     * 获取所有轮播图信息
     * @return
     */
    @RequestMapping(value = "/listBaseBanners",method = RequestMethod.GET)
    public Result<List<PmsBaseBanner>> listBaseBanners(){
        return bannerClient.listBaseBanners();
    }
}
