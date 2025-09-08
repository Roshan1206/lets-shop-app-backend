package com.example.lets_shop_app.mapper;

import com.example.lets_shop_app.dto.response.ProductResponse;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.entity.ProductCategory;

import java.math.BigDecimal;

public class ProductMapper {

    public static Product mapToProduct(String[] productValues, ProductCategory category) {
        Product product = new Product();
        product.setName(productValues[1]);
        product.setDescription(productValues[2]);
        product.setPrice(new BigDecimal(productValues[3]));
        product.setStock(Long.parseLong(productValues[4]));
        product.setBrand(productValues[5]);
        product.setCategory(category);
        product.setThumbnail(productValues[7]);
        return product;
    }

    public static ProductResponse mapToProductResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setThumbnail(product.getThumbnail());
        productResponse.setPrice(product.getPrice());
        productResponse.setBrand(product.getBrand());
        productResponse.setStock(product.getStock());
        productResponse.setCategory(product.getCategory().getProductCategory());
        return productResponse;
    }
}
