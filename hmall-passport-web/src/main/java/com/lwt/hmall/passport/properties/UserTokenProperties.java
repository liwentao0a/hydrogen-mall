package com.lwt.hmall.passport.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author lwt
 * @Date 2020/3/25 20:02
 * @Description
 */
@ConfigurationProperties(prefix = "setting.passport")
public class UserTokenProperties {

    private String key="HYDROGEN_MALL";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
