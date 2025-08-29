package com.example.lets_shop_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
@EnableMethodSecurity
public class LetsShopAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetsShopAppApplication.class, args);
	}

}
