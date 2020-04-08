package com.lwt.hmall.api.constant;

public enum UserStatusEnum {

    ENABLE(1,"启用"),
    DISABLE(0,"禁用");

    private int status;
    private String description;

    UserStatusEnum(int status, String description) {
        this.status=status;
        this.description=description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
