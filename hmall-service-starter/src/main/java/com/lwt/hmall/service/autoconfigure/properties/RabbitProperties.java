package com.lwt.hmall.service.autoconfigure.properties;

/**
 * @Author lwt
 * @Date 2020/4/3 20:03
 * @Description
 */
public class RabbitProperties {

    private boolean enable=true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
