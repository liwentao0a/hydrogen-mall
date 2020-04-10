package com.lwt.hmall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HmallUserWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmallUserWebApplication.class, args);
    }

}
