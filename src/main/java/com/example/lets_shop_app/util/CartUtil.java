package com.example.lets_shop_app.util;

import com.example.lets_shop_app.entity.Cart;

public interface CartUtil {
    Cart getCartItem(long cartId);
    double calculateSingleCartItemPrice(Cart cart);
}
