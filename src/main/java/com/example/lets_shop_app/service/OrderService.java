package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.response.OrderUserResponse;

import java.util.List;


/**
 * Methods to be implemented in service class for to manage Orders
 *
 * @author Roshan
 */
public interface OrderService {

    /**
     * Create new order for user.
     *
     * @param cartId details to create new order
     * @return order id
     */
    long addOrder(long cartId);


    /**
     * Retrieve all orders for specific user
     *
     * @return List of orders
     */
    List<OrderUserResponse> getUserOrder();


    /**
     * Retrieve specific order
     *
     * @param id order id
     * @return order
     */
    OrderUserResponse getUserOrder(long id);
}
