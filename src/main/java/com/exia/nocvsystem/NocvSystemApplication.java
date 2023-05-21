package com.exia.nocvsystem;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@Slf4j
@MapperScan("com.exia.nocvsystem.dao")
@SpringBootApplication
@EnableScheduling//定时启动
public class NocvSystemApplication {

    public static void main(String[] args) {

        SpringApplication.run(NocvSystemApplication.class, args);
    }

}
