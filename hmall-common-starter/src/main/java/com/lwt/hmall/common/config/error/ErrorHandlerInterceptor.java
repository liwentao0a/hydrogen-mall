package com.lwt.hmall.common.config.error;

import com.alibaba.fastjson.JSON;
import com.lwt.hmall.api.util.IsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author lwt
 * @Date 2020/4/6 12:45
 * @Description
 */
public class ErrorHandlerInterceptor implements HandlerInterceptor {

    private static final String HANDLER_METHOD_NAME ="errorHtml";

    private ErrorProperties errorProperties;

    public ErrorHandlerInterceptor(ErrorProperties errorProperties) {
        this.errorProperties=errorProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            request.setAttribute(HANDLER_METHOD_NAME,HANDLER_METHOD_NAME.equals(((HandlerMethod)handler).getMethod().getName()));
        }catch (Exception e){
            request.setAttribute(HANDLER_METHOD_NAME,false);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //判断是否是返回错误页面
        if ((Boolean) request.getAttribute(HANDLER_METHOD_NAME)){
            //设置错误信息cookie
            Map<String, Object> model = modelAndView.getModel();
            StringBuffer params=new StringBuffer();
            if (model!=null&&model.size()>0) {
                params.append("?");
                for (String key : model.keySet()) {
                    Object val = model.get(key);
                    try {
                        String cookieVal = "";
                        if (IsUtils.isBaseType(val) || val instanceof String) {
                            cookieVal = val.toString();
                        } else {
                            cookieVal = JSON.toJSONString(val);
                        }
                        if (!IsUtils.isBlank(cookieVal)) {
                            if (params.length()>1){
                                params.append("&");
                            }
                            params.append(key).append("=").append(cookieVal);
                            Cookie cookie = new Cookie(key, cookieVal);
                            cookie.setMaxAge((int) TimeUnit.SECONDS.convert(1, TimeUnit.HOURS));
                            response.addCookie(cookie);
                        }
                    } catch (Exception e) {
                    }
                }
            }
            if (errorProperties.isRedirect()) {
                modelAndView.setViewName("redirect:"+errorProperties.getRedirectUrl() + params.toString());
            }
        }
    }
}
