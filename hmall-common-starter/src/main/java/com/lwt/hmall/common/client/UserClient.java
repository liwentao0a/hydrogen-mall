package com.lwt.hmall.common.client;

import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.bean.UmsUser;
import com.lwt.hmall.api.bean.UmsUserReceiveAddress;
import com.lwt.hmall.api.group.UmsUserGroup;
import com.lwt.hmall.api.group.UmsUserReceiveAddressGroup;
import com.lwt.hmall.common.client.fallback.UserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@FeignClient(name = "hmall-user-service",contextId = "UserClient",fallbackFactory = UserFallbackFactory.class)
public interface UserClient {

    @RequestMapping(value = "/getUserByUsernameAndPassword",method = RequestMethod.GET)
    public Result<UmsUser> getUserByUsernameAndPassword(@RequestParam("username") @NotBlank String username,
                                                        @RequestParam("password") @NotBlank String password);

    @RequestMapping(value = "/getUserByUsername/{username}",method = RequestMethod.GET)
    public Result<UmsUser> getUserByUsername(@PathVariable("username") @NotBlank String username);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserById/{userId}",method = RequestMethod.GET)
    public Result<UmsUser> getUserById(@PathVariable("userId") @NotNull Long userId);

    @RequestMapping(value = "/registeredUser",method = RequestMethod.POST)
    public Result<UmsUser> registeredUser(@RequestBody @Validated(UmsUserGroup.RegisteredUser.class) UmsUser umsUser);

    /**
     * 获取用户收货地址列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/listUserReceiveAddress",method = RequestMethod.GET)
    public Result<List<UmsUserReceiveAddress>> listUserReceiveAddress(@RequestParam("userId") @NotNull Long userId);

    /**
     * 获取用户及用户收货地址信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserAndReceiveAddress",method = RequestMethod.GET)
    public Result<UmsUser> getUserAndReceiveAddress(@RequestParam("userId") @NotNull Long userId);

    /**
     * 获取收货地址消息
     * @param userId
     * @param receiveId
     * @return
     */
    @RequestMapping(value = "/getUserReceiveAddressByUserIdAndReceiveId",method = RequestMethod.GET)
    public Result<UmsUserReceiveAddress> getUserReceiveAddressByUserIdAndReceiveId(@RequestParam("userId") @NotNull Long userId,
                                                            @RequestParam("receiveId") @NotNull Long receiveId);

    /**
     * 保存用户收货地址
     * @param umsUserReceiveAddress
     * @return
     */
    @RequestMapping(value = "/saveUserReceiveAddress",method = RequestMethod.POST)
    public Result saveUserReceiveAddress(@RequestBody @Validated(UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class) UmsUserReceiveAddress umsUserReceiveAddress);

    /**
     * 删除用户收货地址
     * @param receiveAddressId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/removeUserReceiveAddressByIdAndUserId",method = RequestMethod.DELETE)
    public Result removeUserReceiveAddressByIdAndUserId(@RequestParam("receiveAddressId") @NotNull Long receiveAddressId,
                                                         @RequestParam("userId") @NotNull Long userId);

    /**
     * 设置用户令牌
     * @param userId
     * @param token
     * @return
     */
    @RequestMapping(value = "/setToken",method = RequestMethod.POST)
    public Result setToken(@RequestParam("userId") @NotNull Long userId,
                           @RequestParam("token") @NotBlank String token,
                           @RequestParam("l") @NotNull Long l,
                           @RequestParam("timeUnit") @NotBlank String timeUnit);

    /**
     * 检查用户令牌
     * @param userId
     * @param token
     * @return
     */
    @RequestMapping(value = "/checkToken",method = RequestMethod.GET)
    public Result checkToken(@RequestParam("userId") @NotNull Long userId,
                                     @RequestParam("token") @NotBlank String token);

    /**
     * 删除用户令牌
     * @param userId
     * @param token
     * @return
     */
    @RequestMapping(value = "/removeToken",method = RequestMethod.DELETE)
    public Result removeToken(@RequestParam("userId") @NotNull Long userId,
                              @RequestParam("token") @NotBlank String token);
}
