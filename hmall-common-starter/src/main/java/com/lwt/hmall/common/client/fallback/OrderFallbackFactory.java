package com.lwt.hmall.common.client.fallback;

import com.lwt.hmall.api.bean.OmsOrder;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.common.client.OrderClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/4/7 10:27
 * @Description
 */
@Component
public class OrderFallbackFactory implements FallbackFactory<OrderClient> {
    @Override
    public OrderClient create(Throwable throwable) {
        return new OrderClient() {
            @Override
            public Result<String> createOrderSn() {
                return ResultUtils.fail();
            }

            @Override
            public Result saveOrder(@NotBlank String orderSn, @NotNull @Size(min = 1) List<Long> cartItemIds, @NotNull Long userId, @NotNull Long receiverId) {
                return ResultUtils.fail();
            }

            @Override
            public Result<List<OmsOrder>> listOrdersByUserId(@NotNull Long userId) {
                return ResultUtils.fail();
            }
        };
    }
}
