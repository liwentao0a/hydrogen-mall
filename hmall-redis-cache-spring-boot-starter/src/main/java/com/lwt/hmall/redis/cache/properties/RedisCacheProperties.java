package com.lwt.hmall.redis.cache.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author lwt
 * @Date 2020/4/3 20:04
 * @Description
 */
@ConfigurationProperties(prefix = "settings.redis.cache")
public class RedisCacheProperties {

    private boolean enable=true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
