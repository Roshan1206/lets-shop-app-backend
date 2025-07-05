package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.OrderCreateRequest;
import com.example.lets_shop_app.dto.OrderUserResponse;

import java.util.List;

public interface OrderService {
    long addOrder(OrderCreateRequest orderRequest);
    List<OrderUserResponse> getUserOrder();
}
