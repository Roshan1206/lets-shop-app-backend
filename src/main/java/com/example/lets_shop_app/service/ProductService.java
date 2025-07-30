package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.ProductSaveDto;
import com.example.lets_shop_app.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Methods to be implemented in service class for product related operations.
 *
 * @author Roshan
 */
public interface ProductService {

    /**
     * Get list of products in Page
     *
     * @param pageable contains product page related data
     * @return Product page
     */
    Page<Product> getProducts(Pageable pageable);


    /**
     * Find a specific product by its id
     *
     * @param id product id
     * @return Product
     */
    Product findByProductId(long id);


    /**
     * Add a new product
     *
     * @param product Product
     * @return Product id and name
     */
    ProductSaveDto addProduct(Product product);


    /**
     * Add new products
     *
     * @param products Product
     * @return Product id and name
     */
    List<ProductSaveDto> addAllProduct(List<Product> products);
}
