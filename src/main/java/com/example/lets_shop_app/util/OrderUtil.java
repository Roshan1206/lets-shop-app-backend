package com.example.lets_shop_app.util;

import com.example.lets_shop_app.dto.OrderUserResponse;
import com.example.lets_shop_app.entity.Order;

public interface OrderUtil {

    OrderUserResponse convertToOrderUserResponse(Order order);
}
