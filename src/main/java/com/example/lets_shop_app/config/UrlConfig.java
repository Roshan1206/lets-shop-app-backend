package com.example.lets_shop_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UrlConfig implements  WebMvcConfigurer{
	
	@Value("${allowed.origin}")
	private String[] allowedOrigins;
	
//	@Value("${spring.data.rest.base-path}")
//	private String basePath;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addCorsMappings(registry);
		registry
//				.addMapping(basePath + "/")
				.addMapping("")
				.allowedOrigins(allowedOrigins);
	}
	
	

}
