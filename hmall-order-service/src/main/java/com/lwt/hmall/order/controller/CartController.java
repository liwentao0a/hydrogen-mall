package com.lwt.hmall.order.controller;

import com.lwt.hmall.api.bean.OmsCartItem;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.IsUtils;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/5 13:31
 * @Description
 */
@RestController
@Validated
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 保存sku到购物车
     * @param skuId
     * @param quantity
     * @param userId
     * @param userNickname
     * @return
     */
    @RequestMapping(value = "/saveCartItem",method = RequestMethod.POST)
    public Result saveCartItem(@RequestParam("skuId") @NotNull Long skuId,
                               @RequestParam("quantity") @NotNull Long quantity,
                               @RequestParam("userId") @NotNull Long userId,
                               @RequestParam("userNickname") @NotBlank String userNickname){
        boolean saveCartItem = cartService.saveCartItem(skuId, quantity, userId, userNickname);
        if (saveCartItem){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    /**
     * 获取用户购物车列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/listCartItemsByUserId",method = RequestMethod.GET)
    public Result<List<OmsCartItem>> listCartItemsByUserId(@RequestParam("userId") @NotNull Long userId){
        List<OmsCartItem> omsCartItems = cartService.listCartItemsByUserId(userId);
        if (IsUtils.isBlank(omsCartItems)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(omsCartItems);
    }

    /**
     * 删除购物车商品
     * @param cartItemId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/removeCartItemByCartItemIdAndUserId",method = RequestMethod.DELETE)
    public Result removeCartItemByCartItemIdAndUserId(@RequestParam("cartItemId") @NotNull Long cartItemId,
                                             @RequestParam("userId") @NotNull Long userId){
        if(cartService.removeCartItemByCartItemIdAndUserId(cartItemId,userId)){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    /**
     * 获取购物车商品信息
     * @param cartItemIds
     * @return
     */
    @RequestMapping(value = "/listCartItemsInCartItemIds",method = RequestMethod.GET)
    public Result<List<OmsCartItem>> listCartItemsInCartItemIds(@RequestParam("cartItemIds") @NotNull @Size(min = 1) List<Long> cartItemIds){
        List<OmsCartItem> omsCartItems = cartService.listCartItemsInCartItemIds(cartItemIds);
        if (IsUtils.isBlank(omsCartItems)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(omsCartItems);
    }

    /**
     * 删除购物车商品
     * @param cartItemIds
     * @param userId
     * @return
     */
    @RequestMapping(value = "/removeCartItemByUserIdAndInCartItemIds",method = RequestMethod.DELETE)
    public Result removeCartItemByUserIdAndInCartItemIds(@RequestParam("cartItemIds") @NotNull @Size(min = 1) List<Long> cartItemIds,
                                                          @RequestParam("userId") @NotNull Long userId){
        if (cartService.removeCartItemByUserIdAndInCartItemIds(cartItemIds,userId)){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

}
