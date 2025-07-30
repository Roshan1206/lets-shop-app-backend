package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.dao.ProductRepository;
import com.example.lets_shop_app.dto.ProductSaveDto;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.exception.ProductNotFoundException;
import com.example.lets_shop_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


/**
 * Service class for Product related operations
 *
 * @author Roshan
 */
@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductService {


    /**
     * Injecting {@link ProductRepository} for DB operations
     */
    private final ProductRepository productRepository;


    /**
     * Get list of products in Page
     *
     * @param pageable contains product page related data
     * @return Product page
     */
    @Override
    public Page<Product> getProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }


    /**
     * Find a specific product by its id
     *
     * @param id product id
     * @return Product
     */
    @Override
    public Product findByProductId(long id){
        return productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Product not found with given id: {}", id))
        );

    }

    /**
     * Add a new product
     *
     * @param product Product
     * @return Product id and name
     */
    @Override
    public ProductSaveDto addProduct(Product product){
        Product newProduct = productRepository.save(product);
        return new ProductSaveDto(newProduct.getId(), newProduct.getName());
    }


    /**
     * Add new products
     *
     * @param products Product
     * @return Product id and name
     */
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
