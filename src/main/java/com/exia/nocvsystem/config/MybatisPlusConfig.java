package com.exia.nocvsystem.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value={PaginationInterceptor.class})
public class MybatisPlusConfig {
@Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
