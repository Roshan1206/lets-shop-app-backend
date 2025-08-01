package com.example.lets_shop_app.util.impl;

import com.example.lets_shop_app.dao.CartRepository;
import com.example.lets_shop_app.entity.Cart;
import com.example.lets_shop_app.util.CartUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CartUtilImpl implements CartUtil {

    private final CartRepository cartRepository;

    public CartUtilImpl(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCartItem(long cartId) {
        return cartRepository.findById(cartId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found")
        );
    }

    public double calculateSingleCartItemPrice(Cart cart){
        return cart.getProductPrice() * cart.getProductQuantity();
    }
}
