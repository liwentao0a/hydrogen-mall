package com.lwt.hmall.common.config.error;


import com.lwt.hmall.api.bean.Result;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @Author lwt
 * @Date 2020/4/6 11:11
 * @Description
 */
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        Result result = (Result) webRequest.getAttribute("result", WebRequest.SCOPE_REQUEST);
        if (result!=null){
            errorAttributes.put("code",result.getCode());
            errorAttributes.put("message",result.getMessage());
            errorAttributes.put("data",result.getData());
        }

        return errorAttributes;
    }
}
