package com.github.hstardawn.bighomework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.hstardawn.bighomework.mapper")
public class BigHomeWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigHomeWorkApplication.class, args);
    }

}
