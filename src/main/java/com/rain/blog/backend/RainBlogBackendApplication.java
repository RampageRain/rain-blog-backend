package com.rain.blog.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rain.blog.backend.mapper")
public class RainBlogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RainBlogBackendApplication.class, args);
    }

}
