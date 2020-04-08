package com.lwt.hmall.common.client.fallback;

import com.lwt.hmall.api.bean.OmsCartItem;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.common.client.CartClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/4/7 10:19
 * @Description
 */
@Component
public class CartFallbackFactory implements FallbackFactory<CartClient> {
    @Override
    public CartClient create(Throwable throwable) {
        return new CartClient() {
            @Override
            public Result saveCartItem(@NotNull Long skuId, @NotNull Long quantity, @NotNull Long userId, @NotBlank String userNickname) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result<List<OmsCartItem>> listCartItemsByUserId(@NotNull Long userId) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result removeCartItemByCartItemIdAndUserId(@NotNull Long cartItemId, @NotNull Long userId) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result<List<OmsCartItem>> listCartItemsInCartItemIds(@NotNull @Size(min = 1) List<Long> cartItemIds) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result removeCartItemByUserIdAndInCartItemIds(@NotNull @Size(min = 1) List<Long> cartItemIds, @NotNull Long userId) {
                return ResultUtils.result(CodeEnum.FAIL);
            }
        };
    }
}
