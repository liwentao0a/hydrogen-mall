package com.lwt.hmall.passport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HmallPassportWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmallPassportWebApplication.class, args);
    }

}
