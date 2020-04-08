package com.lwt.hmall.order.controller;

import com.lwt.hmall.api.bean.OmsCartItem;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.bean.UmsUser;
import com.lwt.hmall.api.constant.HeaderEnum;
import com.lwt.hmall.api.util.ServletUtils;
import com.lwt.hmall.common.client.CartClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/5 13:46
 * @Description
 */
@RestController
@Validated
public class CartController {

    @Autowired
    private CartClient cartClient;

    /**
     * 保存sku到购物车
     * @param skuId
     * @param quantity
     * @return
     */
    @RequestMapping(value = "/USER/saveCartItem",method = RequestMethod.POST)
    public Result saveCartItem(@RequestParam("skuId") @NotNull Long skuId,
                               @RequestParam("quantity") @NotNull Long quantity,
                               HttpServletRequest request){
        UmsUser user = (UmsUser) ServletUtils.getHeader(request, HeaderEnum.USER);
        Long userId = user.getId();
        String userNickname = user.getNickname();
        return cartClient.saveCartItem(skuId, quantity, userId, userNickname);
    }

    /**
     * 获取用户购物车列表
     * @return
     */
    @RequestMapping(value = "/USER/listCartItems",method = RequestMethod.GET)
    public Result<List<OmsCartItem>> listCartItems(HttpServletRequest request){
        UmsUser user = (UmsUser) ServletUtils.getHeader(request, HeaderEnum.USER);
        return cartClient.listCartItemsByUserId(user.getId());
    }

    /**
     * 删除购物车商品
     * @param cartItemId
     * @param request
     * @return
     */
    @RequestMapping(value = "/USER/removeCartItemByCartItemId",method = RequestMethod.DELETE)
    public Result removeCartItemByCartItemId(@RequestParam("cartItemId") @NotNull Long cartItemId,
                                             HttpServletRequest request){
        UmsUser user = (UmsUser) ServletUtils.getHeader(request, HeaderEnum.USER);
        return cartClient.removeCartItemByCartItemIdAndUserId(cartItemId,user.getId());
    }

    /**
     * 获取购物车商品信息
     * @param cartItemIds
     * @return
     */
    @RequestMapping(value = "/listCartItemsInCartItemIds",method = RequestMethod.GET)
    public Result<List<OmsCartItem>> listCartItemsInCartItemIds(@RequestParam("cartItemIds") @NotNull @Size(min = 1) List<Long> cartItemIds){
        return cartClient.listCartItemsInCartItemIds(cartItemIds);
    }
}
