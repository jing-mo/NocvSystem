package com.exia.nocvsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/5/12 10:52
 */

@Configuration
public class UploadConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/UserFaces/**").addResourceLocations("file:" + "E:/java/NocvSystem/src/main/resources/static/images/UserFaces/");
    }
}
