package com.lwt.hmall.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.bean.UmsUser;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.constant.HeaderEnum;
import com.lwt.hmall.api.constant.RoleEnum;
import com.lwt.hmall.api.util.JWTUtils;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.api.util.ServletUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author lwt
 * @Date 2020/4/1 11:23
 * @Description
 */
@Component
public class LoginAuthFilter extends ZuulFilter {

    private RestTemplate restTemplate=new RestTemplate();

    private String verifyUrl="http://localhost:8000/verifyToken";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.DEBUG_FILTER_ORDER+1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String servletPath = request.getServletPath();
        System.out.println(servletPath);

        Pattern pattern = Pattern.compile("/\\w*/(?<role>\\w+)/?.*");
        Matcher matcher = pattern.matcher(servletPath);
        if (matcher.find()){
            String role = matcher.group("role");
            try {
                RoleEnum roleEnum = RoleEnum.valueOf(role);
                request.setAttribute("role",roleEnum);
                return true;
            }catch (Exception e){

            }
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        RoleEnum role = (RoleEnum) request.getAttribute("role");

        //获取token
        Cookie userTokenCookie = ServletUtils.getCookie(request, "user-token");
        Result result = null;
        if (userTokenCookie!=null) {
            //校验token
            String userToken = userTokenCookie.getValue();
            String ipAddress = ServletUtils.getIpAddress(request);
            MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("token", userToken);
            paramMap.add("ipAddress", ipAddress);
            Result verifyResult = restTemplate.postForObject(verifyUrl, paramMap, Result.class);
            if (verifyResult.getCode() == CodeEnum.SUCCESS.getCode()) {
                Map<String, Object> body = JWTUtils.getBody(userToken);
                UmsUser user = JSON.parseObject( JSON.parse((String) body.get("user")).toString(), UmsUser.class);
                //权限校验
                if (user.getRoleLevel() >= role.getRoleLevel()) {
                    currentContext.addZuulRequestHeader(HeaderEnum.USER.name(),JSON.toJSONString(user));
                    return null;
                } else {
                    result= ResultUtils.result(CodeEnum.ACCOUNT_INSUFFICIENT_PERMISSIONS);
                }
            }
        }
        if (result==null){
            result=ResultUtils.result(CodeEnum.ACCOUNT_TOKEN_EXCEPTION);
        }

        String servletPath = request.getServletPath();
        Pattern pattern = Pattern.compile("/page/.*");
        Matcher matcher = pattern.matcher(servletPath);
        currentContext.setSendZuulResponse(false);
        if (matcher.find()) {
            try {
                currentContext.getResponse().sendRedirect(request.getServletContext().getContextPath()+"/page/login.html");
            } catch (IOException e) {
                throw new ZuulException(e,HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            }
        }else {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.OK.value());
            currentContext.setResponseBody(JSON.toJSONString(result));
            currentContext.getResponse().setContentType("text/json;charset=utf-8");
        }
        return null;
    }
}
