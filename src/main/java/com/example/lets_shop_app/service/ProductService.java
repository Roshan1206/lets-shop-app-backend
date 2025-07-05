package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.ProductSaveDto;
import com.example.lets_shop_app.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> getProducts(Pageable pageable);
    Product findByProductId(long id);
    ProductSaveDto addProduct(Product product);
    List<ProductSaveDto> addAllProduct(List<Product> products);
}
