package com.lwt.hmall.service.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @Author lwt
 * @Date 2020/4/3 19:29
 * @Description
 */
@ConfigurationProperties(prefix = "settings.service")
public class ServiceConfigProperties {

    private boolean enable=true;
    @NestedConfigurationProperty
    private RabbitProperties rabbit;
    @NestedConfigurationProperty
    private RedisProperties redis;

}
