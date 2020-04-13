package com.lwt.hmall.common.client.fallback;

import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.bean.UmsUser;
import com.lwt.hmall.api.bean.UmsUserReceiveAddress;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.common.client.UserClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/4/7 10:31
 * @Description
 */
@Component
public class UserFallbackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public Result<UmsUser> getUserByUsernameAndPassword(@NotBlank String username, @NotBlank String password) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result<UmsUser> getUserByUsername(@NotBlank String username) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result<UmsUser> getUserById(@NotNull Long userId) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result<UmsUser> registeredUser(UmsUser umsUser) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result<List<UmsUserReceiveAddress>> listUserReceiveAddress(@NotNull Long userId) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result<UmsUser> getUserAndReceiveAddress(@NotNull Long userId) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result<UmsUserReceiveAddress> getUserReceiveAddressByUserIdAndReceiveId(@NotNull Long userId, @NotNull Long receiveId) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result saveUserReceiveAddress(UmsUserReceiveAddress umsUserReceiveAddress) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result removeUserReceiveAddressByIdAndUserId(@NotNull Long receiveAddressId, @NotNull Long userId) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result setToken(@NotNull Long userId, @NotBlank String token, @NotNull Long l, @NotBlank String timeUnit) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result checkToken(@NotNull Long userId, @NotBlank String token) {
                return ResultUtils.result(CodeEnum.FAIL);
            }

            @Override
            public Result removeToken(@NotNull Long userId, @NotBlank String token) {
                return ResultUtils.result(CodeEnum.FAIL);
            }
        };
    }
}
