package com.lwt.hmall.api.util;

import com.alibaba.fastjson.JSON;
import com.lwt.hmall.api.constant.HeaderEnum;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @Author lwt
 * @Date 2020/2/5 18:09
 * @Description
 */
public class ServletUtils {

    public static Cookie getCookie(HttpServletRequest request,String name){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length>0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)){
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址。
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if("127.0.0.1".equals(ip)||"0:0:0:0:0:0:0:1".equals(ip)){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        return ip;
    }

    public static<T> T getHeader(HttpServletRequest request,String name,Class<T> clazz){
        String header = request.getHeader(name);
        T t = JSON.parseObject(header, clazz);
        return t;
    }

    public static Object getHeader(HttpServletRequest request, HeaderEnum headerEnum){
        return ServletUtils.getHeader(request,headerEnum.name(), headerEnum.getClazz());
    }
}
