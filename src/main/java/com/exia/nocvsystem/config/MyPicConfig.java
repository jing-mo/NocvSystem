package com.exia.nocvsystem.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
  当上传图片时不能马上显示，后台接收图片保存到本地，返回保存路径，发现页面的标签无法显示图片，
F12显示无法加载图片，请求地址为ip:port/static/uploadFile（楼主将图片保存到了static下），
显示404无此资源。将项目重新启动之后，图片可以正常加载。
        原因分析：当程序加载后自动会加载到内存中，对当前目录不做读取。
        解决方案就是设置虚拟目录*/
@Configuration
public class MyPicConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("\\images\\**").
                addResourceLocations("E:\\JAVA\\NocvSystem\\src\\main\\resources\\static\\images\\UserFaces\\");
    }
}

