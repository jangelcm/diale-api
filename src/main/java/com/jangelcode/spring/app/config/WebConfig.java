package com.jangelcode.spring.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/preparados-magistrales/**", "/public/comprobantes/**")
                .addResourceLocations("file:public/preparados-magistrales/", "file:public/comprobantes/");
    }
}