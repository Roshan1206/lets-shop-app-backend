package com.example.lets_shop_app.util.impl;

import com.example.lets_shop_app.dao.CartItemRepository;
import com.example.lets_shop_app.dao.CartRepository;
import com.example.lets_shop_app.entity.CartItem;
import com.example.lets_shop_app.util.CartUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Component
public class CartUtilImpl implements CartUtil {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartUtilImpl(CartRepository cartRepository, CartItemRepository cartItemRepository){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem getCartItem(long cartId) {
        return cartItemRepository.findById(cartId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found")
        );
    }

    @Override
    public BigDecimal calculateCartItemAmount(int quantity, BigDecimal price) {
        return BigDecimal.valueOf(quantity).multiply(price);
    }


}
