package com.example.lets_shop_app.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class EntityConfig implements RepositoryRestConfigurer{
	private final EntityManager entityManager;

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
		exposeIds(config);
	}

	private void exposeIds(RepositoryRestConfiguration config) {
		config.exposeIdsFor(entityManager.getMetamodel()
											.getEntities()
											.stream()
											.map(e -> e.getJavaType())
											.collect(Collectors.toList())
											.toArray(new Class[0])
				);
		
		
	}
	
	

}
