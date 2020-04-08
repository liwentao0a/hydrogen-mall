package com.lwt.hmall.common.client.fallback;

import com.lwt.hmall.api.bean.OmsOrderItem;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.common.client.WareClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/4/7 10:32
 * @Description
 */
@Component
public class WareFallbackFactory implements FallbackFactory<WareClient> {
    @Override
    public WareClient create(Throwable throwable) {
        return new WareClient() {
            @Override
            public Result<Integer> getAvailableWareSkuStockBySkuId(@NotNull Long skuId) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result lockWareSkuByOrderItems(@NotNull @Size(min = 1) List<OmsOrderItem> omsOrderItems) {
                return ResultUtils.result(CodeEnum.FAIL);
            }
        };
    }
}
