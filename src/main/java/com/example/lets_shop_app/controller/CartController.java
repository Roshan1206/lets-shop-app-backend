package com.example.lets_shop_app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lets_shop_app.entity.Cart;
import com.example.lets_shop_app.model.CartItemsResponse;
import com.example.lets_shop_app.model.CartRequest;
import com.example.lets_shop_app.model.CartResponse;
import com.example.lets_shop_app.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/carts")
public class CartController {
	
	private final CartService cartService;
	
	@GetMapping("/cart-items")
	public List<CartItemsResponse> getCartItems(Principal principal) {
		return cartService.getCartItems(principal);
	}
	
	@PostMapping("/add-to-cart")
	public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest cart, Principal principal){
		System.out.println(cart);
		cartService.addToCart(cart, principal);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/decrement-cart-item")
	public ResponseEntity<CartResponse> decrementCartItemQuantity(@RequestBody Cart cart, Principal principal){
		cartService.decrementCartItem(cart, principal);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/remove-cart-item")
	public ResponseEntity<Cart> deleteCartItem(@RequestBody Cart cart, Principal principal){
		cartService.removeCartItem(cart, principal);
		return ResponseEntity.ok().build();
	}

}
