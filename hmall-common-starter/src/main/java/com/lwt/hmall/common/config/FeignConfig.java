package com.lwt.hmall.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lwt
 * @Date 2020/2/3 16:03
 * @Description
 */
@Configuration
@ConditionalOnClass(FeignAutoConfiguration.class)
@ConditionalOnProperty(value = "settings.common.feign.enable",havingValue = "false",matchIfMissing = true)
@EnableFeignClients(value = "com.lwt.hmall.common.client",defaultConfiguration = FeignConfig.class)
@ComponentScan("com.lwt.hmall.common.client")
public class FeignConfig {

}
