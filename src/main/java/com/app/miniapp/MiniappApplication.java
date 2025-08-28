package com.app.miniapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.app.miniapp.feign.client")
@ServletComponentScan // 扫描 @WebFilter
public class MiniappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniappApplication.class, args);
    }

}
