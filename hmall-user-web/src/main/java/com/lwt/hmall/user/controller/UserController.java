package com.lwt.hmall.user.controller;

import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.bean.UmsUser;
import com.lwt.hmall.api.bean.UmsUserReceiveAddress;
import com.lwt.hmall.api.constant.HeaderEnum;
import com.lwt.hmall.api.group.UmsUserGroup;
import com.lwt.hmall.api.group.UmsUserReceiveAddressGroup;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.api.util.ServletUtils;
import com.lwt.hmall.common.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/2/5 17:22
 * @Description
 */
@RestController
@Validated
public class UserController {

    @Autowired
    private UserClient userClient;

    /**
     * 获取用户信息
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/getUserByUsernameAndPassword",method = RequestMethod.GET)
    public Result<UmsUser> getUserByUsernameAndPassword(@RequestParam("username") @NotBlank String username,
                                                        @RequestParam("password") @NotBlank String password){
        return userClient.getUserByUsernameAndPassword(username,password);
    }

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    @RequestMapping(value = "/ADMIN/getUserByUsername/{username}",method = RequestMethod.GET)
    public Result<UmsUser> getUserByUsername(@PathVariable("username") @NotBlank String username){
        return userClient.getUserByUsername(username);
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/USER/getUserInfo",method = RequestMethod.GET)
    public Result<UmsUser> getUserInfo(HttpServletRequest request){
        UmsUser user = (UmsUser) ServletUtils.getHeader(request, HeaderEnum.USER);
        return userClient.getUserByUsername(user.getUsername());
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/registeredUser",method = RequestMethod.POST)
    public Result<UmsUser> registeredUser(@RequestBody @Validated(UmsUserGroup.RegisteredUser.class) UmsUser user){
        return userClient.registeredUser(user);
    }

    /**
     * 获取简单的用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/USER/getSimpleUserInfo",method = RequestMethod.GET)
    public Result<UmsUser> getSimpleUserInfo(HttpServletRequest request){
        UmsUser user = (UmsUser) ServletUtils.getHeader(request, HeaderEnum.USER);
        return ResultUtils.success(user);
    }

    /**
     * 获取用户收货地址列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/USER/listUserReceiveAddress",method = RequestMethod.GET)
    public Result<List<UmsUserReceiveAddress>> listUserReceiveAddress(HttpServletRequest request){
        UmsUser user = (UmsUser) ServletUtils.getHeader(request, HeaderEnum.USER);
        return userClient.listUserReceiveAddress(user.getId());
    }

    /**
     * 保存用户收货地址
     * @param umsUserReceiveAddress
     * @return
     */
    @RequestMapping(value = "/USER/saveUserReceiveAddress",method = RequestMethod.POST)
    public Result saveUserReceiveAddress(@RequestBody @Validated(UmsUserReceiveAddressGroup.SaveUserReceiveAddressNotUserId.class) UmsUserReceiveAddress umsUserReceiveAddress,
                                         HttpServletRequest request){
        UmsUser user = (UmsUser) ServletUtils.getHeader(request, HeaderEnum.USER);
        umsUserReceiveAddress.setUserId(user.getId());
        return userClient.saveUserReceiveAddress(umsUserReceiveAddress);
    }

    /**
     * 删除用户收货地址
     * @param receiveAddressId
     * @param request
     * @return
     */
    @RequestMapping(value = "/USER/removeUserReceiveAddressById",method = RequestMethod.DELETE)
    public Result removeUserReceiveAddressById(@RequestParam("receiveAddressId") @NotNull Long receiveAddressId,
                                                         HttpServletRequest request){
        UmsUser user = (UmsUser) ServletUtils.getHeader(request, HeaderEnum.USER);
        return userClient.removeUserReceiveAddressByIdAndUserId(receiveAddressId,user.getId());
    }

}
