package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.dao.ProductRepository;
import com.example.lets_shop_app.dto.ProductSaveDto;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.exception.ProductNotFoundException;
import com.example.lets_shop_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> getProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }


    @Override
    public Product findByProductId(long id){
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with given id: {}" + id)
        );
    }


    @Override
    public ProductSaveDto addProduct(Product product){
        Product newProduct = productRepository.save(product);
        return new ProductSaveDto(newProduct.getId(), newProduct.getName());
    }


    @Override
    public List<ProductSaveDto> addAllProduct(List<Product> products){
        List<Product> productList = productRepository.saveAll(products);
        List<ProductSaveDto> productSaveDtoList = new ArrayList<>();

        productList.forEach(product -> {
           ProductSaveDto productSaveDto = new ProductSaveDto(product.getId(), product.getName());
           productSaveDtoList.add(productSaveDto);
        });
        return productSaveDtoList;
    }
}
