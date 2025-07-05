package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.CartResponse;
import com.example.lets_shop_app.dto.CartRequest;

import java.util.List;

public interface CartService {

    CartResponse addToCart(CartRequest cartRequest);
    void incrementCartItem(long cartId);
    void decrementCartItem(long cartId);
    void removeCartItem(long cartId);
    List<CartResponse> getCartItems();
}
