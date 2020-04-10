package com.lwt.hmall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lwt.hmall.order.mapper")
public class HmallOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmallOrderServiceApplication.class, args);
    }

}
