package com.example.lets_shop_app.util.impl;

import com.example.lets_shop_app.repository.ProductRepository;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.util.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ProductUtilImpl implements ProductUtil {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product getProduct(long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found.")
        );
    }
}
