package com.example.lets_shop_app.mapper;

import com.example.lets_shop_app.dto.response.CartResponse;
import com.example.lets_shop_app.entity.CartItem;

public class CartMapper {

    public static CartResponse mapToCartResponse(CartItem cartItem) {
        return new CartResponse(cartItem.getId(), cartItem.getProduct(), cartItem.getTotalPrice(), cartItem.getProductQuantity());
    }
}
