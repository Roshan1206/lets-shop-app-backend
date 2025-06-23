package com.example.lets_shop_app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lets_shop_app.dto.OrderCreateRequest;
import com.example.lets_shop_app.dto.OrderUserResponse;
import com.example.lets_shop_app.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;
	
	@PostMapping("/create-order")
	public ResponseEntity<Long> createOrder(
			@RequestBody OrderCreateRequest orderCreateRequest, 
			Principal principal){
		return ResponseEntity.ok(orderService.addOrder(orderCreateRequest, principal));
	}
	
	@GetMapping("/get-orders")
	public ResponseEntity<List<OrderUserResponse>> getUserOrder(Principal principal){
		return ResponseEntity.ok(orderService.getUserOrder(principal));
	}
}
