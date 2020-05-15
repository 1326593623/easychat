package com.magee.easychat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(value = "com.magee.easychat.mapper")
@SpringBootApplication
public class EasychatApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasychatApplication.class, args);
    }

}
