package com.lwt.hmall.common.config.error;

import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.ResultUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author lwt
 * @Date 2020/4/6 11:11
 * @Description
 */
@ControllerAdvice
@ConditionalOnProperty(value = "settings.common.error.enable",havingValue = "false",matchIfMissing = true)
public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forward:/error");
        if (e instanceof RuntimeException){
            modelAndView.addObject("result", ResultUtils.result(CodeEnum.SYSTEM_ERROR));
        }
        modelAndView.addObject("javax.servlet.error.status_code",HttpStatus.INTERNAL_SERVER_ERROR.value());
        return modelAndView;
    }
}
