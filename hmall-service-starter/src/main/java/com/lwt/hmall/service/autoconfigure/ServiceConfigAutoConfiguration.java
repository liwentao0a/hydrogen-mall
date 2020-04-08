package com.lwt.hmall.service.autoconfigure;

import com.lwt.hmall.service.autoconfigure.properties.ServiceConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lwt
 * @Date 2020/4/3 19:28
 * @Description
 */
@Configuration
@EnableConfigurationProperties(ServiceConfigProperties.class)
@ComponentScan("com.lwt.hmall.service.config")
public class ServiceConfigAutoConfiguration {

}
