package com.lwt.hmall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lwt.hmall.ware.mapper")
public class HmallWareServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmallWareServiceApplication.class, args);
    }

}
