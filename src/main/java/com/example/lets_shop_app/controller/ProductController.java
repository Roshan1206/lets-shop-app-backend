package com.example.lets_shop_app.controller;

import com.example.lets_shop_app.dto.ProductSaveDto;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20") int size,
                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "true") boolean asc){
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(productsService.findByProductId(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductSaveDto> addProduct(@RequestBody Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productsService.addProduct(product));
    }

    @PostMapping("/add-all")
    public ResponseEntity<List<ProductSaveDto>> addAllProduct(@RequestBody List<Product> products){
        return ResponseEntity.status(HttpStatus.CREATED).body(productsService.addAllProduct(products));
    }
}
