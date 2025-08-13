package com.example.lets_shop_app.controller;

import java.util.List;

import com.example.lets_shop_app.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.lets_shop_app.entity.Cart;
import com.example.lets_shop_app.dto.CartResponse;
import com.example.lets_shop_app.dto.CartRequest;
import com.example.lets_shop_app.dto.CartResponse;

import lombok.RequiredArgsConstructor;


/**
 * Controller class for Cart related operations.
 *
 * @author Roshan
 */
@Tag(name = "Cart Endpoints", description = "Operational REST API endpoints related to Cart")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/carts")
public class CartController {

	/**
	 * Injecting {@link CartService}
	 */
	private final CartService cartService;


	/**
	 * API to get all user cart items.
	 *
	 * @return List of cart items
	 */
	@GetMapping
	public ResponseEntity<?> getCartItems() {
		List<CartResponse> items =  cartService.getCart();
		if (items.isEmpty()){
			return ResponseEntity.status(HttpStatus.OK).body("Your Cart is empty.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(items);
	}


	/**
	 * API to create new cart item.
	 *
	 * @param cart item to be added in cart
	 * @return cart item
	 */
	@PostMapping
	public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest cart){
		System.out.println(cart);
		CartResponse cartItemsResponse = cartService.addToCart(cart);
		return ResponseEntity.status(HttpStatus.CREATED).body(cartItemsResponse);
	}


	/**
	 * API to increment any particular cart item count
	 *
	 * @param cartId cart id
	 */
	@PutMapping("/{cartId}/increment")
	public ResponseEntity<CartResponse> incrementCartItemQuantity(@PathVariable long cartId){
		cartService.decrementCartItem(cartId);
		return ResponseEntity.ok().build();
	}


	/**
	 * API to decrement any particular cart item count
	 *
	 * @param cartId cart id
	 */
	@PostMapping("/{cartId}/decrement")
	public ResponseEntity<CartResponse> decrementCartItemQuantity(@PathVariable long cartId){
		cartService.incrementCartItem(cartId);
		return ResponseEntity.ok().build();
	}


	/**
	 * API to delete any particular cart item.
	 *
	 * @param cartId cart id
	 */
	@DeleteMapping("/{cartId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCartItem(@PathVariable long cartId){
		cartService.removeCartItem(cartId);
	}

}
