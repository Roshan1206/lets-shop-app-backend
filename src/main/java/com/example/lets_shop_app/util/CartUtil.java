package com.example.lets_shop_app.util;

import com.example.lets_shop_app.entity.CartItem;

import java.math.BigDecimal;

public interface CartUtil {
    CartItem getCartItem(long cartId);
    BigDecimal calculateCartItemAmount(int quantity, BigDecimal price);
}
