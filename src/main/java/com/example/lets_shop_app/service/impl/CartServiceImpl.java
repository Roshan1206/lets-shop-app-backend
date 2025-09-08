package com.example.lets_shop_app.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.lets_shop_app.repository.CartItemRepository;
import com.example.lets_shop_app.entity.CartItem;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.mapper.CartMapper;
import com.example.lets_shop_app.service.CartService;
import com.example.lets_shop_app.util.CartUtil;
import com.example.lets_shop_app.util.ProductUtil;
import com.example.lets_shop_app.util.UserUtil;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.repository.CartRepository;
import com.example.lets_shop_app.entity.Cart;
import com.example.lets_shop_app.dto.response.CartResponse;
import com.example.lets_shop_app.dto.request.CartRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartUtil cartUtil;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductUtil productUtil;
	private final UserUtil userUtil;


	/**
	 * Create a new cart item in user carts
	 *
	 * @param cartRequest item to be added in cart
	 * @return created cart item
	 */
	@Override
	public CartResponse addToCart(CartRequest cartRequest) {
		Cart newCart;
		Optional<Cart> existingCart = cartRepository.findByUserId(userUtil.getUserId());

		if(existingCart.isEmpty()){
			newCart = new Cart();
			cartRepository.save(newCart);
		}else {
			newCart = existingCart.get();
		}

		CartItem cartItem = new CartItem();
		cartItem.setCart(newCart);

		Product product = productUtil.getProduct(cartRequest.getProductId());
		BigDecimal productPrice = cartUtil.calculateCartItemAmount(cartRequest.productQuantity, product.getPrice());

		cartItem.setProduct(product);
		cartItem.setProductPrice(productPrice);
		cartItem.setProductQuantity(cartRequest.getProductQuantity());

		CartItem savedCartItem = cartItemRepository.save(cartItem);
		return CartMapper.mapToCartResponse(savedCartItem);
	}

	/**
	 * Increment the product quantity by 1 in the cart
	 *
	 * @param cartId id
	 */
	@Override
	public void incrementCartItem(long cartId) {
		CartItem cartItem = cartUtil.getCartItem(cartId);

		int quantity = cartItem.getProductQuantity() + 1;
		BigDecimal price = cartUtil.calculateCartItemAmount(quantity, cartItem.getProductPrice());
		cartItem.setProductQuantity(quantity);
		cartItem.setTotalPrice(price);
		cartItemRepository.save(cartItem);
	}

	/**
	 * Decrement the product quantity by 1 in the cart
	 *
	 * @param cartId id
	 */
	@Override
	public void decrementCartItem(long cartId) {
		CartItem cartItem = cartUtil.getCartItem(cartId);

		int quantity = cartItem.getProductQuantity() - 1;

		if (quantity == 0){
			removeCartItem(cartId);
			return;
		}

		BigDecimal price = cartUtil.calculateCartItemAmount(quantity, cartItem.getProductPrice());
		cartItem.setProductQuantity(quantity);
		cartItem.setTotalPrice(price);
		cartItemRepository.save(cartItem);
	}

	/**
	 * Delete the selected cart item
	 *
	 * @param cartId id
	 */
	@Override
	public void removeCartItem(long cartId) {
		cartItemRepository.deleteById(cartId);
	}

	/**
	 * Get all cart items for current users
	 *
	 * @return List of cart items
	 */
	@Override
	public List<CartResponse> getCart() {
		Optional<List<CartItem>> cartItems = cartItemRepository.findAllByUserId(userUtil.getUserId());

        return cartItems.map(items -> items.stream().map(CartMapper::mapToCartResponse).toList()).orElseGet(List::of);
    }
}
