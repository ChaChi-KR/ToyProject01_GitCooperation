package com.example.schedule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;

    @Autowired
    public WebConfig(LoginCheckInterceptor loginCheckInterceptor) {
        this.loginCheckInterceptor = loginCheckInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/api/schedules/**")
                .excludePathPatterns(
                        "/api/auth/login",
                        "/api/auth/logout",
                        "/login.html",
                        "/schedule.html",
                        "/css/**",
                        "/js/**",
                        "/error"
                );
    }

    /*
    프론트를 다른 포트(예: Live Server 5500)에서 띄울 때만 필요합니다.
    지금처럼 Spring Boot static 안에서 같이 서비스하면 필요 없습니다.

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5500")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
    */
}