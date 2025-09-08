package com.example.lets_shop_app.util.impl;

import com.example.lets_shop_app.dto.response.OrderUserResponse;
import com.example.lets_shop_app.entity.Order;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.util.OrderUtil;
import com.example.lets_shop_app.util.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderUtilImpl implements OrderUtil {

    @Autowired
    private ProductUtil productUtil;

    @Override
    public OrderUserResponse convertToOrderUserResponse(Order order) {
        Product product = productUtil.getProduct(order.getProductId());
        OrderUserResponse response = new OrderUserResponse();
        response.setId(order.getId());
        response.setProductId(product.getId());
        response.setProductName(product.getName());
        response.setProductThumbnail(product.getThumbnail());
        response.setProductPrice(order.getProductPrice());
        response.setTotalPrice(order.getTotalPrice());
        response.setCreatedDate(order.getCreatedAt());
        return response;
    }
}
