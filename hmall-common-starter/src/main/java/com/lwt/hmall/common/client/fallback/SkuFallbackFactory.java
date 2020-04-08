package com.lwt.hmall.common.client.fallback;

import com.lwt.hmall.api.bean.Page;
import com.lwt.hmall.api.bean.PmsSkuInfo;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.common.client.SkuClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/4/7 10:30
 * @Description
 */
@Component
public class SkuFallbackFactory implements FallbackFactory<SkuClient> {
    @Override
    public SkuClient create(Throwable throwable) {
        return new SkuClient() {
            @Override
            public Result<Page<PmsSkuInfo>> pageSkuInfos(@NotNull Integer pageNum, @NotNull Integer pageSize) {
                return ResultUtils.fail();
            }

            @Override
            public Result<Page<PmsSkuInfo>> pageSkuInfosByCatalog3(Long catalog3Id, @NotNull Integer pageNum, @NotNull Integer pageSize) {
                return ResultUtils.fail();
            }

            @Override
            public Result<Page<PmsSkuInfo>> pageSkuInfosByKeyword(@NotNull String keyword, @NotNull Integer pageNum, @NotNull Integer pageSize) {
                return ResultUtils.fail();
            }

            @Override
            public Result<List<PmsSkuInfo>> listSkusByProductId(@NotNull Long productId) {
                return ResultUtils.fail();
            }

            @Override
            public Result<PmsSkuInfo> getSkuBySkuId(@NotNull Long skuId) {
                return ResultUtils.fail();
            }

            @Override
            public Result<PmsSkuInfo> getSkuInfoBySkuId(@NotNull Long skuId) {
                return ResultUtils.fail();
            }

            @Override
            public Result<BigDecimal> getSkuPriceBySkuId(@NotNull Long skuId) {
                return ResultUtils.fail();
            }
        };
    }
}
