package com.lwt.hmall.common.client;

import com.lwt.hmall.api.bean.OmsOrder;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.common.client.fallback.OrderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@FeignClient(name ="hmall-order-service",contextId = "OrderClient",fallbackFactory = OrderFallbackFactory.class)
public interface OrderClient {

    /*
    order
     */

    /**
     * 生成订单令牌
     * @return
     */
    @RequestMapping(value = "/createOrderSn",method = RequestMethod.GET)
    public Result<String> createOrderSn();

    /**
     * 保存订单
     * @param orderSn
     * @param cartItemIds
     * @param userId
     * @return
     */
    @RequestMapping(value = "/saveOrder",method = RequestMethod.POST)
    public Result saveOrder(@RequestParam("orderSn") @NotBlank String orderSn,
                            @RequestParam("cartItemIds") @NotNull @Size(min = 1) List<Long> cartItemIds,
                            @RequestParam("userId") @NotNull Long userId,
                            @RequestParam("receiverId") @NotNull Long receiverId);

    /**
     * 获取订单和订单详情
     * @param userId
     * @return
     */
    @RequestMapping(value = "/listOrdersByUserId",method = RequestMethod.GET)
    public Result<List<OmsOrder>> listOrdersByUserId(@RequestParam("userId") @NotNull Long userId);
}
