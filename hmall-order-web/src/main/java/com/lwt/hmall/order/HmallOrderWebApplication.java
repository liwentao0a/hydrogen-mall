package com.lwt.hmall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HmallOrderWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmallOrderWebApplication.class, args);
    }

}
