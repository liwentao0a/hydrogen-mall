package com.lwt.hmall.api.constant;

public enum CodeEnum {

    FAIL(0,"请求失败"),
    SUCCESS(1,"请求成功"),
    RETURN_FALSE(2,"返回false"),
    RETURN_NULL(3,"返回null"),

    ACCOUNT_USERNAME_OR_PASSWORD_ERROR(1000,"账户用户名或密码错误"),
    ACCOUNT_STATUS_ABNORMAL(1001,"账户状态异常"),
    ACCOUNT_INSUFFICIENT_PERMISSIONS(1002,"账户权限不足"),
    ACCOUNT_TOKEN_EXCEPTION(1003,"账户令牌异常"),
    ACCOUNT_USERNAME_ALREADY_EXISTS(1003,"账户用户名已存在"),

    SYSTEM_ERROR(2001,"系统异常"),

    SERVICE_TOKEN_EXCEPTION(3001,"服务令牌异常");

    private Integer code;
    private String message;

    CodeEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
