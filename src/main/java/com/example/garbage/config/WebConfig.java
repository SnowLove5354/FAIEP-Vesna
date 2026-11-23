package com.example.garbage.config;
import com.example.garbage.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired private LoginInterceptor loginInterceptor;
    @Autowired private AdminInterceptor adminInterceptor;
    @Override public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/record", "/record/**", "/quiz", "/quiz/**", "/points", "/points/**");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**").excludePathPatterns("/admin/login");
    }
}
