package com.lwt.hmall.error.autoconfigure;

import com.lwt.hmall.error.ErrorAttributes;
import com.lwt.hmall.error.ErrorExceptionHandler;
import com.lwt.hmall.error.ErrorHandlerInterceptor;
import com.lwt.hmall.error.properties.ErrorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author lwt
 * @Date 2020/4/10 17:11
 * @Description
 */
@Configuration
@ConditionalOnProperty(value = "settings.error.enable",havingValue = "false",matchIfMissing = true)
@EnableConfigurationProperties(ErrorProperties.class)
public class ErrorAutoConfiguration implements WebMvcConfigurer {

    @Autowired
    private ErrorProperties errorProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ErrorHandlerInterceptor(errorProperties)).addPathPatterns("/error").order(1);
    }

    @Bean
    public DefaultErrorAttributes defaultErrorAttributes(){
        return new ErrorAttributes();
    }

    @Bean
    public HandlerExceptionResolver exceptionHandler(){
        return new ErrorExceptionHandler();
    }
}
