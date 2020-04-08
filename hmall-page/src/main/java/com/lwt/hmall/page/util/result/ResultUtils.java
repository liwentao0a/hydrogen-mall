package com.lwt.hmall.page.util.result;

/**
 * @Author lwt
 * @Date 2020/2/5 13:23
 * @Description
 */
public class ResultUtils {

    public static<T> Result<T> result(CodeEnum codeEnum,T data){
        return new Result<T>(codeEnum.getCode(),codeEnum.getMessage(),data);
    }

    public static<T> Result<T> result(CodeEnum codeEnum){
        return result(codeEnum,null);
    }

    public static<T> Result<T> success(T data){
        return new Result<T>(CodeEnum.SUCCESS.getCode(),CodeEnum.SUCCESS.getMessage(),data);
    }

    public static<T> Result<T> success(){
        return success(null);
    }
}
