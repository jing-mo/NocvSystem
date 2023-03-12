package com.exia.nocvsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.exia.nocvsystem.dao")
@SpringBootApplication
@EnableScheduling//定时启动
public class NocvSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(NocvSystemApplication.class, args);
    }

}
