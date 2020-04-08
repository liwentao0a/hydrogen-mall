package com.lwt.hmall.passport.controller;

import com.alibaba.fastjson.JSON;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.bean.UmsUser;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.constant.RoleEnum;
import com.lwt.hmall.api.constant.UserStatusEnum;
import com.lwt.hmall.api.util.JWTUtils;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.api.util.ServletUtils;
import com.lwt.hmall.common.client.UserClient;
import com.lwt.hmall.passport.properties.UserTokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lwt
 * @Date 2020/2/6 20:14
 * @Description
 */
@RestController
@Validated
@EnableConfigurationProperties(UserTokenProperties.class)
public class PassportController {

    @Autowired
    private UserTokenProperties userTokenProperties;

    @Autowired
    private UserClient userClient;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginForUser",method = RequestMethod.POST)
    public Result<String> loginForUser(@RequestParam("username") @NotBlank String username,
                                       @RequestParam("password") @NotBlank String password,
                                       HttpServletRequest request){
        return getLoginResult(username, password, request, RoleEnum.USER.getRoleLevel());
    }

    /**
     * 管理员登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginForAdmin",method = RequestMethod.POST)
    public Result<String> loginForAdmin(@RequestParam("username") @NotBlank String username,
                                @RequestParam("password") @NotBlank String password,
                                HttpServletRequest request){
        return getLoginResult(username, password, request, RoleEnum.ADMIN.getRoleLevel());
    }

    /**
     * 用户令牌校验
     * @param token
     * @param ipAddress
     * @param request
     * @return
     */
    @RequestMapping(value = "/verifyToken",method = RequestMethod.POST)
    public Result verifyToken(@RequestParam("token") @NotBlank String token,
                                        @RequestParam("ipAddress") @NotBlank String ipAddress,
                                        HttpServletRequest request){
        Map<String, Object> body = JWTUtils.verifyToken(userTokenProperties.getKey(), ipAddress, token);
        if (body==null){
            return ResultUtils.result(CodeEnum.RETURN_FALSE);
        }
        return ResultUtils.success();
    }

    /**
     * 获取登录结果
     * @param username
     * @param password
     * @param request
     * @param roleLevel
     * @return
     */
    private Result<String> getLoginResult(String username, String password, HttpServletRequest request, int roleLevel) {
        //获取用户信息
        Result<UmsUser> userResult = userClient.getUserByUsernameAndPassword(username, password);
        if (userResult.getCode()!= CodeEnum.SUCCESS.getCode()) {
            return ResultUtils.result(CodeEnum.ACCOUNT_USERNAME_OR_PASSWORD_ERROR);
        }
        UmsUser data = userResult.getData();
        //判断账户状态
        if (data.getStatus()!= UserStatusEnum.ENABLE.getStatus()){
            return ResultUtils.result(CodeEnum.ACCOUNT_STATUS_ABNORMAL);
        }
        //判断账户权限级别
        if (data.getRoleLevel()< roleLevel){
            return ResultUtils.result(CodeEnum.ACCOUNT_INSUFFICIENT_PERMISSIONS);
        }
        //生成用户令牌
        UmsUser user = new UmsUser();
        user.setId(data.getId());
        user.setUsername(data.getUsername());
        user.setNickname(data.getNickname());
        user.setRoleLevel(data.getRoleLevel());

        String ipAddress = ServletUtils.getIpAddress(request);

        Map<String,Object> body=new HashMap<>();
        body.put("user", JSON.toJSONString(user));
        String token = JWTUtils.createToken(userTokenProperties.getKey(), ipAddress, body);
        return ResultUtils.success(token);
    }

}
