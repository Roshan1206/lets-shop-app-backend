package com.example.lets_shop_app.util;

import com.example.lets_shop_app.dto.CartRequest;
import com.example.lets_shop_app.dto.CartResponse;
import com.example.lets_shop_app.entity.Cart;
import com.example.lets_shop_app.entity.CartItem;

import java.math.BigDecimal;

public interface CartUtil {
    CartItem getCartItem(long cartId);
    CartResponse converToCartResponse(CartItem cartItem);
    BigDecimal calculateCartItemAmount(int quantity, BigDecimal price);
}
