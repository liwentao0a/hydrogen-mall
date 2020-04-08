package com.lwt.hmall.common.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @Author lwt
 * @Date 2020/4/3 19:29
 * @Description
 */
@ConfigurationProperties(prefix = "settings.common")
public class CommonConfigProperties {

    private boolean enable=true;
    @NestedConfigurationProperty
    private FeignProperties feign;

}
