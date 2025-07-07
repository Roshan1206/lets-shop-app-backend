package com.example.lets_shop_app.controller;

import java.util.List;

import com.example.lets_shop_app.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lets_shop_app.dto.OrderCreateRequest;
import com.example.lets_shop_app.dto.OrderUserResponse;

import lombok.RequiredArgsConstructor;

@Tag(name = "Order Endpoints", description = "Operational REST API endpoints related to Order")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;
	
	@PostMapping
	public ResponseEntity<Long> createOrder(@RequestBody OrderCreateRequest orderCreateRequest){
		return ResponseEntity.ok(orderService.addOrder(orderCreateRequest));
	}
	
	@GetMapping
	public ResponseEntity<List<OrderUserResponse>> getUserOrder(){
		return ResponseEntity.ok(orderService.getUserOrder());
	}
}
