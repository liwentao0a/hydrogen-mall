package com.lwt.hmall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HmallProductWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmallProductWebApplication.class, args);
    }

}
