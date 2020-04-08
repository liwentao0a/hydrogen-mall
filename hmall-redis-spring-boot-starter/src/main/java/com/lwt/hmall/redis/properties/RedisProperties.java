package com.lwt.hmall.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author lwt
 * @Date 2020/4/7 16:01
 * @Description
 */
@ConfigurationProperties(prefix = "settings.redis")
public class RedisProperties {

    private boolean enable=true;
}
