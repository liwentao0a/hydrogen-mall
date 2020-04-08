package com.lwt.hmall.api.util;

import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;

/**
 * @Author lwt
 * @Date 2020/2/5 13:23
 * @Description
 */
public class ResultUtils {

    public static<T> Result<T> result(CodeEnum codeEnum, T data){
        return new Result<T>(codeEnum.getCode(),codeEnum.getMessage(),data);
    }

    public static<T> Result<T> result(CodeEnum codeEnum){
        return result(codeEnum,null);
    }

    public static<T> Result<T> success(T data){
        return result(CodeEnum.SUCCESS,data);
    }

    public static<T> Result<T> success(){
        return success(null);
    }

    public static<T> Result<T> fail(T data){
        return result(CodeEnum.FAIL,data);
    }

    public static<T> Result<T> fail(){
        return success(null);
    }
}
