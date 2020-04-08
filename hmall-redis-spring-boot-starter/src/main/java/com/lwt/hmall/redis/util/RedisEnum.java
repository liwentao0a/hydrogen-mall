package com.lwt.hmall.redis.util;

public enum RedisEnum {

    HMALL_ORDERSERVICE_CREATEORDERTOKEN(RedisUtils.KEY_PREFIX,"OrderService","createOrderToken");

    private String prefix;
    private String module;
    private String func;

    RedisEnum(String prefix,String module, String func) {
        this.prefix=prefix;
        this.module=module;
        this.func=func;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }
}
