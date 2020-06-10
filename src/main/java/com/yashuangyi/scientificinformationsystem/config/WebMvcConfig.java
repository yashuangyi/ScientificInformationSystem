package com.yashuangyi.scientificinformationsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//扩展SpringMVC功能
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //视图映射
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/papers/**").addResourceLocations("file:C:/sources/papers/");
        registry.addResourceHandler("/results/**").addResourceLocations("file:C:/sources/results/");
    }

}
