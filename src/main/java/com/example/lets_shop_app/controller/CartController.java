package com.example.lets_shop_app.controller;

import java.util.List;

import com.example.lets_shop_app.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.lets_shop_app.entity.Cart;
import com.example.lets_shop_app.dto.CartResponse;
import com.example.lets_shop_app.dto.CartRequest;
import com.example.lets_shop_app.dto.CartResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
	
	private final CartService cartService;
	
	@GetMapping
	public List<CartResponse> getCartItems() {
		return cartService.getCartItems();
	}
	
	@PostMapping
	public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest cart){
		System.out.println(cart);
		CartResponse cartItemsResponse = cartService.addToCart(cart);
		return ResponseEntity.status(HttpStatus.CREATED).body(cartItemsResponse);
	}

	@PutMapping("/increment/{cartId}")
	public ResponseEntity<CartResponse> incrementCartItemQuantity(@PathVariable long cartId){
		cartService.decrementCartItem(cartId);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/decrement/{cartId}")
	public ResponseEntity<CartResponse> decrementCartItemQuantity(@PathVariable long cartId){
		cartService.incrementCartItem(cartId);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{cartId}")
	public void deleteCartItem(@PathVariable long cartId){
		cartService.removeCartItem(cartId);
	}

}
