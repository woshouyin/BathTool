package com.fred;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fred.repo.mapper")
public class BathClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BathClientApplication.class, args);
        System.out.println("bath client has been start !");
    }
}
