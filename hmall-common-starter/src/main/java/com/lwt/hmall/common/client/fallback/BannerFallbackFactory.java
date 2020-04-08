package com.lwt.hmall.common.client.fallback;

import com.lwt.hmall.api.bean.PmsBaseBanner;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.common.client.BannerClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/4/7 10:14
 * @Description
 */
@Component
public class BannerFallbackFactory implements FallbackFactory<BannerClient> {
    @Override
    public BannerClient create(Throwable throwable) {
        return new BannerClient() {
            @Override
            public Result<List<PmsBaseBanner>> listBaseBanners() {
                return ResultUtils.result(CodeEnum.FAIL);
            }
        };
    }
}
