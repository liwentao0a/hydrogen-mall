package com.lwt.hmall.error;

import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ConditionalOnProperty(value = "settings.error.enable",havingValue = "false",matchIfMissing = true)
public class ErrorExceptionHandler implements HandlerExceptionResolver {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.error("已处理异常",e);
        ModelAndView modelAndView = new ModelAndView();
        //根据异常类型进行处理
        if (e instanceof RuntimeException){
            modelAndView.addObject("result", ResultUtils.result(CodeEnum.SYSTEM_ERROR));
        }
        //设置状态码
        modelAndView.addObject("javax.servlet.error.status_code",HttpStatus.INTERNAL_SERVER_ERROR.value());
        //跳转到默认错误处理控制器
        modelAndView.setViewName("forward:/error");
        return modelAndView;
    }
}
