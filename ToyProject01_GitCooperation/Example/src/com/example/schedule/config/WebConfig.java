package com.example.schedule.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final LoginCheckInterceptor loginCheckInterceptor;

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
						"/error",
						"/favicon.ico"
				);
	}
}