package com.lwt.hmall.zuul.filter;

import com.lwt.hmall.api.util.IsUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author lwt
 * @Date 2020/4/1 13:42
 * @Description 跨域请求过滤器
 */
@Component
public class CrossFilter extends ZuulFilter {

    private boolean allowCredentials=true;
    private String[] allowedOrigins={};
    private String[] allowedMethods={"GET","POST","PUT","DELETE"};
    private String[] allowedHeaders={"Content-Type","Content-Length"};
    private String[] exposedHeaders={};
    private long maxAge=604800000;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.DEBUG_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        //判断是否跨域
        String origin = request.getHeader("origin");
        if (!StringUtils.isBlank(origin)) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();

        String origin = request.getHeader("origin");
        //设置跨域响应头
        if (allowCredentials) {
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        if (!IsUtils.isBlank(allowedOrigins)){
            response.setHeader("Access-Control-Allow-Origin",StringUtils.join(allowedOrigins,","));
        }else{
            response.setHeader("Access-Control-Allow-Origin",origin);
        }
        if (!IsUtils.isBlank(allowedMethods)) {
            response.setHeader("Access-Control-Allow-Methods", StringUtils.join(allowedMethods,","));
        }
        if (!IsUtils.isBlank(allowedHeaders)) {
            response.setHeader("Access-Control-Allow-Headers", StringUtils.join(allowedHeaders,","));
        }
        if (!IsUtils.isBlank(exposedHeaders)) {
            response.setHeader("Access-Control-Expose-Headers", StringUtils.join(exposedHeaders,","));
        }
        response.setHeader("Access-Control-Max-Age",String.valueOf(maxAge));
        //判断是否是预检请求
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.OK.value());
            currentContext.setResponseBody(HttpStatus.OK.name());
        }
        return null;
    }
}
