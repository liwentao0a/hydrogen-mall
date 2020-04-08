package com.lwt.hmall.common.config.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author lwt
 * @Date 2020/4/6 12:47
 * @Description
 */
@Configuration
@ConditionalOnProperty(value = "settings.common.error.enable",havingValue = "false",matchIfMissing = true)
@EnableConfigurationProperties(ErrorProperties.class)
public class ErrorConfig implements WebMvcConfigurer {

    @Autowired
    private ErrorProperties errorProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ErrorHandlerInterceptor(errorProperties)).addPathPatterns("/error").order(1);
    }

    @Bean
    public DefaultErrorAttributes defaultErrorAttributes(){
        return new MyErrorAttributes();
    }

}
