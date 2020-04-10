package com.lwt.hmall.zuul.filter;

import com.lwt.hmall.api.util.ServletUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author lwt
 * @Date 2020/4/1 17:28
 * @Description 对一些参数进行包装
 */
@Component
public class HeaderDecorationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        String ipAddress = ServletUtils.getIpAddress(request);
        if (ipAddress!=null){
            System.out.println(ipAddress+"-3");
            currentContext.addZuulRequestHeader("x-forwarded-for",ipAddress);
            Map<String, String> zuulRequestHeaders = currentContext.getZuulRequestHeaders();
            zuulRequestHeaders.put("x-forwarded-for",ipAddress);
        }

        Cookie userTokenCookie = ServletUtils.getCookie(request, "user-token");
        if (userTokenCookie!=null){
            currentContext.addZuulRequestHeader("x-user-token",userTokenCookie.getValue());
        }

        return null;
    }
}
