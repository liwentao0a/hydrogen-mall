package com.lwt.hmall.order.controller;

import com.lwt.hmall.api.bean.OmsOrder;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.bean.UmsUser;
import com.lwt.hmall.api.constant.HeaderEnum;
import com.lwt.hmall.api.util.ServletUtils;
import com.lwt.hmall.common.client.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/11 20:40
 * @Description
 */
@RestController
@Validated
public class OrderController {

    @Autowired
    private OrderClient orderClient;

    /**
     * 生成订单令牌
     * @return
     */
    @RequestMapping(value = "/createOrderSn",method = RequestMethod.GET)
    public Result<String> createOrderSn(){
        return orderClient.createOrderSn();
    }

    /**
     * 保存订单
     * @param orderSn
     * @param cartItemIds
     * @param receiverId
     * @param request
     * @return
     */
    @RequestMapping(value = "/USER/saveOrder",method = RequestMethod.POST)
    public Result saveOrder(@RequestParam("orderSn") @NotBlank String orderSn,
                             @RequestParam("cartItemIds") @NotNull @Size(min = 1) List<Long> cartItemIds,
                             @RequestParam("receiverId") @NotNull Long receiverId,
                             HttpServletRequest request) {
        UmsUser user = (UmsUser) ServletUtils.getHeader(request, HeaderEnum.USER);
        return orderClient.saveOrder(orderSn,cartItemIds,user.getId(),receiverId);
    }

    /**
     * 获取订单和订单详情
     * @return
     */
    @RequestMapping(value = "/USER/listOrders",method = RequestMethod.GET)
    public Result<List<OmsOrder>> listOrders(HttpServletRequest request){
        UmsUser user = (UmsUser) ServletUtils.getHeader(request, HeaderEnum.USER);
        return orderClient.listOrdersByUserId(user.getId());

    }
}
