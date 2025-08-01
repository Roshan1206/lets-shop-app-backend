package com.example.lets_shop_app.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.lets_shop_app.exception.CartNotFoundException;
import com.example.lets_shop_app.service.CartService;
import com.example.lets_shop_app.util.CartUtil;
import com.example.lets_shop_app.util.UserUtil;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.dao.CartRepository;
import com.example.lets_shop_app.dao.ProductRepository;
import com.example.lets_shop_app.entity.Cart;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.dto.CartResponse;
import com.example.lets_shop_app.dto.CartRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartUtil cartUtil;
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final UserUtil userUtil;


	/**
	 * Create a new cart item in user carts
	 *
	 * @param cartRequest item to be added in cart
	 * @return created cart item
	 */
	@Override
	public CartResponse addToCart(CartRequest cartRequest) {
		String userEmail = userUtil.getAuthenticatedUserEmail();
		Cart existingCart = findCartItem(cartRequest.getProductId(), userEmail);
		Cart newCart;

		if(existingCart != null) {
			int productQuantity = existingCart.getProductQuantity();
			existingCart.setProductQuantity(++productQuantity);
			final double totalProductPrice = existingCart.getProductPrice()*existingCart.getProductQuantity();
			existingCart.setTotalProductPrice(totalProductPrice);
			cartRepository.delete(existingCart);
			newCart = cartRepository.save(existingCart);
		}else {
			Cart cart = new Cart();
			Optional<Product> product = productRepository.findById(cartRequest.getProductId());

			cart.setEmail(userEmail);
			cart.setProductQuantity(1);
			cart.setProductId(product.get().getId());
			cart.setProductName(product.get().getName());
			cart.setProductPrice(product.get().getPrice());
			cart.setTotalProductPrice(product.get().getPrice());
			cart.setProductThumbnail(product.get().getThumbnail());

			newCart = cartRepository.save(cart);
		}
		
		return convertToCartItemResponse(newCart);
	}


	/**
	 * Increment the product quantity by 1 in the cart
	 */
	@Override
	public void incrementCartItem(long cartId) {
		Cart existingCart = findExistingCartItem(cartId);
		int productQuantity = existingCart.getProductQuantity();

		existingCart.setProductQuantity(++productQuantity);
		double price = cartUtil.calculateSingleCartItemPrice(existingCart);
		existingCart.setTotalProductPrice(price);
		cartRepository.save(existingCart);
	}


	/**
	 * Decrement the product quantity by 1 in the cart
	 */
	@Override
	public void decrementCartItem(long cartId) {
		Cart existingCart = findExistingCartItem(cartId);
		int productQuantity = existingCart.getProductQuantity();
		
		if(productQuantity > 1) {
			existingCart.setProductQuantity(--productQuantity);
			double price = cartUtil.calculateSingleCartItemPrice(existingCart);
			existingCart.setTotalProductPrice(price);
			cartRepository.save(existingCart);
		}else {
			removeCartItem(existingCart.getId());
		}
	}


	/**
	 * Delete the selected cart item
	 *
	 * @param cartId id
	 */
	@Override
	public void removeCartItem(long cartId) {
		Cart existingCart = findExistingCartItem(cartId);
		cartRepository.delete(existingCart);
	}


	@Override
	public List<CartResponse> getCartItems(){
		String userEmail = userUtil.getAuthenticatedUserEmail();
		List<Cart> cartItems = cartRepository.findByEmail(userEmail);
		
		return cartItems.stream().map(this::convertToCartItemResponse).toList();
	}

	private Cart findCartItem(Long productId, String email) {
		return cartRepository.findByProductIdAndEmail(productId, email);
	}

	private Cart findExistingCartItem(long cartId){
		return cartRepository.findById(cartId).orElseThrow(
				() -> new CartNotFoundException("The specified cart does not exist")
		);
	}
	
	private CartResponse convertToCartItemResponse(Cart cart){
		CartResponse cartResponse = new CartResponse();
		cartResponse.setId(cart.getId());
		cartResponse.setProductId(cart.getProductId());
		cartResponse.setProductName(cart.getProductName());
		cartResponse.setProductThumbnail(cart.getProductThumbnail());
		cartResponse.setProductPrice(cart.getProductPrice());
		cartResponse.setTotalProductPrice(cart.getTotalProductPrice());
		cartResponse.setProductQuantity(cart.getProductQuantity());
		return cartResponse;
	}
}
