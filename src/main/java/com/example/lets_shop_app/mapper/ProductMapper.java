package com.example.lets_shop_app.mapper;

import com.example.lets_shop_app.dto.ProductDto;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.entity.ProductCategory;
import org.springframework.data.domain.Page;

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

    public static ProductDto mapToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setThumbnail(product.getThumbnail());
        productDto.setPrice(product.getPrice());
        productDto.setBrand(product.getBrand());
        productDto.setStock(product.getStock());
        productDto.setCategory(product.getCategory().getProductCategory());
        return productDto;
    }
}
