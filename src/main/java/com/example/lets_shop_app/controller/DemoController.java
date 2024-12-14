package com.example.lets_shop_app.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@GetMapping("/hello-world")
	public String sayHello(Principal principal) {
		return principal.getName();
	}
}
