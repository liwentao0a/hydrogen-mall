package com.lwt.hmall.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HmallEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmallEurekaApplication.class, args);
    }

}
