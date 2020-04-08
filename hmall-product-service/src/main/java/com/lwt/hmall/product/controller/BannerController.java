package com.lwt.hmall.product.controller;

import com.lwt.hmall.api.bean.PmsBaseBanner;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.IsUtils;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.product.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/2/26 11:06
 * @Description
 */
@RestController
@Validated
@CrossOrigin
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 获取所有轮播图信息
     * @return
     */
    @RequestMapping(value = "/listBaseBanners",method = RequestMethod.GET)
    public Result<List<PmsBaseBanner>> listBaseBanners(){
        List<PmsBaseBanner> pmsBaseBanners = bannerService.listBaseBanners();
        if (IsUtils.isBlank(pmsBaseBanners)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(pmsBaseBanners);
    }
}
