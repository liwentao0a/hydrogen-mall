package com.lwt.hmall.common.client;

import com.lwt.hmall.api.bean.OmsCartItem;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.common.client.fallback.CartFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/14 15:25
 * @Description
 */
@FeignClient(name ="hmall-order-service",contextId = "CartClient",fallbackFactory = CartFallbackFactory.class)
public interface CartClient {

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
                               @RequestParam("userNickname") @NotBlank String userNickname);

    /**
     * 获取用户购物车列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/listCartItemsByUserId",method = RequestMethod.GET)
    public Result<List<OmsCartItem>> listCartItemsByUserId(@RequestParam("userId") @NotNull Long userId);

    /**
     * 删除购物车商品
     * @param cartItemId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/removeCartItemByCartItemIdAndUserId",method = RequestMethod.DELETE)
    public Result removeCartItemByCartItemIdAndUserId(@RequestParam("cartItemId") @NotNull Long cartItemId,
                                                      @RequestParam("userId") @NotNull Long userId);

    /**
     * 获取购物车商品信息
     * @param cartItemIds
     * @return
     */
    @RequestMapping(value = "/listCartItemsInCartItemIds",method = RequestMethod.GET)
    public Result<List<OmsCartItem>> listCartItemsInCartItemIds(@RequestParam("cartItemIds") @NotNull @Size(min = 1) List<Long> cartItemIds);

    /**
     * 删除购物车商品
     * @param cartItemIds
     * @param userId
     * @return
     */
    @RequestMapping(value = "/removeCartItemByUserIdAndInCartItemIds",method = RequestMethod.DELETE)
    public Result removeCartItemByUserIdAndInCartItemIds(@RequestParam("cartItemIds") @NotNull @Size(min = 1) List<Long> cartItemIds,
                                                          @RequestParam("userId") @NotNull Long userId);
}
