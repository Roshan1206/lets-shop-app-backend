package com.example.lets_shop_app.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.lets_shop_app.dao.CartRepository;
import com.example.lets_shop_app.dao.ProductRepository;
import com.example.lets_shop_app.entity.Cart;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.model.CartItemsResponse;
import com.example.lets_shop_app.model.CartRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	
	public Cart findCartItem(Long productId, String email) {
		return cartRepository.findByProductIdAndEmail(productId, email);
	}
	
	public void addToCart(CartRequest cartRequest, Principal principal) {
		Cart existingCart = findCartItem(cartRequest.getProductId(), principal.getName());

		if(existingCart != null) {
			int productQuantity = existingCart.getProductQuantity();
			existingCart.setProductQuantity(++productQuantity);
			final double totalProductPrice = existingCart.getProductPrice()*existingCart.getProductQuantity();
			existingCart.setTotalProductPrice(totalProductPrice);
			cartRepository.delete(existingCart);
			cartRepository.save(existingCart);
		}else {
			Cart cart = new Cart();
			Optional<Product> product = productRepository.findById(cartRequest.getProductId());
			final double totalProductPrice = product.get().getPrice()*cartRequest.getProductQuantity();

			cart.setEmail(principal.getName());
			cart.setProductQuantity(1);
			cart.setProductId(product.get().getId());
			cart.setProductName(product.get().getName());
			cart.setProductPrice(product.get().getPrice());
			cart.setTotalProductPrice(totalProductPrice);
			cart.setProductThumbnail(product.get().getThumbnail());

			cartRepository.save(cart);
		}
	}
	
	public void decrementCartItem(Cart cart, Principal principal) {
		Cart existingCart = findCartItem(cart.getProductId(), principal.getName());
		int productQuantity = existingCart.getProductQuantity();
		
		if(productQuantity > 1) {
			existingCart.setProductQuantity(--productQuantity);
			cartRepository.delete(existingCart);
			cartRepository.save(existingCart);
		}else {
			removeCartItem(existingCart, principal);
		}
	}
	
	public void removeCartItem(Cart cart, Principal principal) {
		Cart existingCart = findCartItem(cart.getProductId(), principal.getName());
		cartRepository.delete(existingCart);		
	}
	
	public List<CartItemsResponse> getCartItems(Principal principal){
		List<Cart> cartItems = cartRepository.findByEmail(principal.getName());
		List<CartItemsResponse> cartItemsResponses = new ArrayList<>();
		
		for (Cart cart : cartItems) {
			CartItemsResponse tempCartItemsResponse = new CartItemsResponse(
						cart.getProductId(),
						cart.getProductName(),
						cart.getProductThumbnail(),
						cart.getProductQuantity(),
						cart.getProductPrice(),
						cart.getTotalProductPrice()
					);
			cartItemsResponses.add(tempCartItemsResponse);
		}
		return cartItemsResponses;
	}
}
