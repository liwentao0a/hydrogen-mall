package com.lwt.hmall.user.controller;

import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.bean.UmsUser;
import com.lwt.hmall.api.bean.UmsUserReceiveAddress;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.group.UmsUserGroup;
import com.lwt.hmall.api.group.UmsUserReceiveAddressGroup;
import com.lwt.hmall.api.util.IsUtils;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

;

/**
 * @Author lwt
 * @Date 2020/2/5 13:16
 * @Description
 */
@RestController
@Validated
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUserByUsernameAndPassword",method = RequestMethod.GET)
    public Result<UmsUser> getUserByUsernameAndPassword(@RequestParam("username") @NotBlank String username,
                                                        @RequestParam("password") @NotBlank String password){
        UmsUser user = userService.getUserByUsernameAndPassword(username, password);
        if (IsUtils.isBlank(user)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(user);
    }

    @RequestMapping(value = "/getUserByUsername/{username}",method = RequestMethod.GET)
    public Result<UmsUser> getUserByUsername(@PathVariable("username") @NotBlank String username){
        UmsUser user = userService.getUserByUsername(username);
        if (IsUtils.isBlank(user)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(user);
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserById/{userId}",method = RequestMethod.GET)
    public Result<UmsUser> getUserById(@PathVariable("userId") @NotNull Long userId){
        UmsUser user = userService.getUserById(userId);
        if (IsUtils.isBlank(user)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(user);
    }

    @RequestMapping(value = "/registeredUser",method = RequestMethod.POST)
    public Result<UmsUser> registeredUser(@RequestBody @Validated(UmsUserGroup.RegisteredUser.class) UmsUser umsUser){
        UmsUser user = userService.getUserByUsername(umsUser.getUsername());
        if (user!=null){
            return ResultUtils.result(CodeEnum.ACCOUNT_USERNAME_ALREADY_EXISTS);
        }
        boolean b = userService.saveUser(umsUser);
        if (b){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    /**
     * 获取用户收货地址列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/listUserReceiveAddress",method = RequestMethod.GET)
    public Result<List<UmsUserReceiveAddress>> listUserReceiveAddress(@RequestParam("userId") @NotNull Long userId){
        List<UmsUserReceiveAddress> umsUserReceiveAddresses = userService.listUserReceiveAddress(userId);
        if (IsUtils.isBlank(umsUserReceiveAddresses)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(umsUserReceiveAddresses);
    }

    /**
     * 获取用户及用户收货地址信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserAndReceiveAddress",method = RequestMethod.GET)
    public Result<UmsUser> getUserAndReceiveAddress(@RequestParam("userId") @NotNull Long userId){
        UmsUser user = userService.getUserAndReceiveAddress(userId);
        if (IsUtils.isBlank(user)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(user);
    }

    /**
     * 获取收货地址消息
     * @param userId
     * @param receiveId
     * @return
     */
    @RequestMapping(value = "/getUserReceiveAddressByUserIdAndReceiveId",method = RequestMethod.GET)
    public Result<UmsUserReceiveAddress> getUserReceiveAddressByUserIdAndReceiveId(@RequestParam("userId") @NotNull Long userId,
                                                                           @RequestParam("receiveId") @NotNull Long receiveId){
        UmsUserReceiveAddress umsUserReceiveAddress = userService.getUserReceiveAddressByUserIdAndReceiveId(userId, receiveId);
        if (umsUserReceiveAddress==null){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(umsUserReceiveAddress);
    }

    /**
     * 保存用户收货地址
     * @param umsUserReceiveAddress
     * @return
     */
    @RequestMapping(value = "/saveUserReceiveAddress",method = RequestMethod.POST)
    public Result saveUserReceiveAddress(@RequestBody @Validated(UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class) UmsUserReceiveAddress umsUserReceiveAddress){
        if(userService.saveUserReceiveAddress(umsUserReceiveAddress)){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    /**
     * 删除用户收货地址
     * @param receiveAddressId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/removeUserReceiveAddressByIdAndUserId",method = RequestMethod.DELETE)
    public Result removeUserReceiveAddressByIdAndUserId(@RequestParam("receiveAddressId") @NotNull Long receiveAddressId,
                                                         @RequestParam("userId") @NotNull Long userId){
        if (userService.removeUserReceiveAddressByIdAndUserId(receiveAddressId,userId)){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    @RequestMapping("/test/{i}")
    public Object test(@PathVariable("i")Integer i){
        if (i<0){
            throw new RuntimeException("aaaaaaa");
        }
        return "ok";
    }

}
