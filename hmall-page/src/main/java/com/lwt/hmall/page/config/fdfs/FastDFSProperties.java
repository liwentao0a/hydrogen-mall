package com.lwt.hmall.page.config.fdfs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author lwt
 * @Date 2020/3/21 9:58
 * @Description
 */
@Component
@ConfigurationProperties(prefix = "setting.fdfs")
public class FastDFSProperties {

    private String protocol = "http://";
    private String separator = "/";
    private String addr = "118.178.194.239";
    private String port = "8999";
    private String config="fdfs_client.conf";

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
