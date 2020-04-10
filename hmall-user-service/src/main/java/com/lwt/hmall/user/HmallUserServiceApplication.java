package com.lwt.hmall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lwt.hmall.user.mapper")
public class HmallUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmallUserServiceApplication.class, args);
    }

}
