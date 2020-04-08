package com.lwt.hmall.common.config.error;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author lwt
 * @Date 2020/4/8 11:09
 * @Description
 */
@ConfigurationProperties(prefix = "settings.error")
public class ErrorProperties {

    private boolean enable=true;
    private boolean redirect=true;
    private String redirectUrl="http://localhost:9021/page/err";

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
