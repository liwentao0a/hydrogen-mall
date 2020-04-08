package com.lwt.hmall.api.constant;

/**
 * @Author lwt
 * @Date 2020/2/5 17:43
 * @Description
 */
public enum  RoleEnum {

    USER(1,"USER"),
    ADMIN(100,"ADMIN");

    private int roleLevel;
    private String name;

    RoleEnum(int roleLevel,String name) {
        this.roleLevel=roleLevel;
        this.name=name;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
