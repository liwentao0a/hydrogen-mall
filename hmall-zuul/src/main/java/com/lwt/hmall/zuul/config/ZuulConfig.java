package com.lwt.hmall.zuul.config;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lwt
 * @Date 2020/4/1 11:20
 * @Description
 */
@Configuration
public class ZuulConfig {

    /**
     * 修改zuul默认匹配规则
     * 匹配 hmall-<名字>-web
     * 路径 /名字/**
     * @return
     */
    @Bean
    public PatternServiceRouteMapper patternServiceRouteMapper(){
//        return new PatternServiceRouteMapper("hmall-(?<name>\\w+)(-)?\\w*","${name}");
        return new PatternServiceRouteMapper("hmall-(?<name>\\w+)-web","${name}");
    }

}
