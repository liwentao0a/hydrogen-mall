package com.lwt.hmall.api.constant;

import com.lwt.hmall.api.bean.UmsUser;

public enum HeaderEnum {

    USER(UmsUser.class);

    private Class clazz;

    HeaderEnum(Class clazz) {
        this.clazz=clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
