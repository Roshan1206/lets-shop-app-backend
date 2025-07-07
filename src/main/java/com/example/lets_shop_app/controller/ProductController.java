package com.example.lets_shop_app.controller;

import com.example.lets_shop_app.dto.ProductSaveDto;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Endpoints", description = "Operational REST API endpoints related to Product")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20") int size,
                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "true") boolean asc){
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByProductId(id));
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    public ResponseEntity<ProductSaveDto> addProduct(@RequestBody Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(product));
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/add-all")
    public ResponseEntity<List<ProductSaveDto>> addAllProduct(@RequestBody List<Product> products){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addAllProduct(products));
    }
}
