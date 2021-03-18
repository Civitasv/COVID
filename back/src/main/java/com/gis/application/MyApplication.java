package com.gis.application;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MyApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MyApplication.class);
    }

    public static void main(String[] args) {
        // Spring应用启动
        SpringApplication application = new SpringApplication();
        // 懒初始化
        application.setLazyInitialization(true);
        SpringApplication.run(MyApplication.class, args);
    }
}