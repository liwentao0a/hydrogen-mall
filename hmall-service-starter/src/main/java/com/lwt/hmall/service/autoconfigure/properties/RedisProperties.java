package com.lwt.hmall.service.autoconfigure.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @Author lwt
 * @Date 2020/4/3 20:03
 * @Description
 */
public class RedisProperties {

    private boolean enable=true;
    @NestedConfigurationProperty
    private CacheProperties cache;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
