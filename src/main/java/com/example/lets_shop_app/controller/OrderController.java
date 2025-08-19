package com.example.lets_shop_app.controller;

import java.util.List;

import com.example.lets_shop_app.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.lets_shop_app.dto.OrderUserResponse;

import lombok.RequiredArgsConstructor;


/**
 * Operational REST Endpoints for Orders.
 *
 * @author Roshan
 */
@Tag(name = "Order Endpoints", description = "Operational REST API endpoints related to Order")
@RestController
@RequestMapping("/orders")
public class OrderController {


	/**
	 * Injecting {@link OrderService}
	 */
	private OrderService orderService;


	public OrderController(OrderService orderService){
		this.orderService = orderService;
	}


	/**
	 * Creating new Order for user
	 *
	 * @param cartId Order details
	 * @return order id
	 */
	@PostMapping
	public ResponseEntity<Long> createOrder(@RequestParam long cartId){
		return ResponseEntity.ok(orderService.addOrder(cartId));
	}


	/**
	 * Retrieve user orders
	 *
	 * @return list of user orders
	 */
	@GetMapping
	public ResponseEntity<List<OrderUserResponse>> getUserOrders(){
		return ResponseEntity.ok(orderService.getUserOrder());
	}


	/**
	 * Retrieve user orders
	 *
	 * @return list of user orders
	 */
	@GetMapping("/{id}")
	public ResponseEntity<OrderUserResponse> getUserOrder(@PathVariable Long id){
		return ResponseEntity.ok(orderService.getUserOrder(id));
	}
}
