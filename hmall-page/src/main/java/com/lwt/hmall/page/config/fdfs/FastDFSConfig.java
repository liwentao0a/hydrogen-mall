package com.lwt.hmall.page.config.fdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author lwt
 * @Date 2020/3/21 9:56
 * @Description
 */
@Configuration
@EnableConfigurationProperties(FastDFSProperties.class)
public class FastDFSConfig {

    private FastDFSProperties fastDFSProperties;

    public FastDFSConfig(FastDFSProperties fastDFSProperties) throws IOException, MyException {
        this.fastDFSProperties=fastDFSProperties;

        ClientGlobal.init(fastDFSProperties.getConfig());
        System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());
    }

    @Bean
    public FastDFSClient fastDFSClient() throws IOException {
        return new FastDFSClient(
                fastDFSProperties.getProtocol(),
                fastDFSProperties.getSeparator(),
                fastDFSProperties.getAddr(),
                fastDFSProperties.getPort());
    }
}
