package com.lwt.hmall.common.autoconfigure;

import com.lwt.hmall.common.autoconfigure.properties.CommonConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lwt
 * @Date 2020/4/3 19:28
 * @Description
 */
@Configuration
@EnableConfigurationProperties(CommonConfigProperties.class)
@ComponentScan("com.lwt.hmall.common.config")
public class CommonConfigAutoConfiguration {

}
