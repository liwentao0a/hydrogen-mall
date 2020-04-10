package com.lwt.hmall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HmallWareWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmallWareWebApplication.class, args);
    }

}
