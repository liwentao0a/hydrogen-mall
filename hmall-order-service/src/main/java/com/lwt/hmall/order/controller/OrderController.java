package com.lwt.hmall.order.controller;

import com.lwt.hmall.api.bean.OmsOrder;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.IsUtils;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/11 20:33
 * @Description
 */
@RestController
@Validated
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单编号
     * @return
     */
    @RequestMapping(value = "/createOrderSn",method = RequestMethod.GET)
    public Result<String> createOrderSn(){
        String orderToken = orderService.createOrderSn();
        return ResultUtils.success(orderToken);
    }

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
                             @RequestParam("receiverId") @NotNull Long receiverId) {
        if(orderService.saveOrder(orderSn,cartItemIds,userId,receiverId)){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    /**
     * 获取订单和订单详情
     * @param userId
     * @return
     */
    @RequestMapping(value = "/listOrdersByUserId",method = RequestMethod.GET)
    public Result<List<OmsOrder>> listOrdersByUserId(@RequestParam("userId") @NotNull Long userId){
        List<OmsOrder> omsOrders = orderService.listOrdersByUserId(userId);
        if (IsUtils.isBlank(omsOrders)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(omsOrders);
    }
}
