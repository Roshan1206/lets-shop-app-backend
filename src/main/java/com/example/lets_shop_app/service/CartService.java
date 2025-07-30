package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.CartResponse;
import com.example.lets_shop_app.dto.CartRequest;

import java.util.List;


/**
 * Interface for operations related to cart
 *
 * @author Roshan
 */
public interface CartService {


    /**
     * Create a new cart item in user carts
     *
     * @param cartRequest item to be added in cart
     * @return created cart item
     */
    CartResponse addToCart(CartRequest cartRequest);


    /**
     * Increment the product quantity by 1 in the cart
     *
     * @param cartId id
     */
    void incrementCartItem(long cartId);


    /**
     * Decrement the product quantity by 1 in the cart
     *
     * @param cartId id
     */
    void decrementCartItem(long cartId);


    /**
     * Delete the selected cart item
     *
     * @param cartId id
     */
    void removeCartItem(long cartId);


    /**
     * Get all cart items for current users
     *
     * @return List of cart items
     */
    List<CartResponse> getCartItems();
}
