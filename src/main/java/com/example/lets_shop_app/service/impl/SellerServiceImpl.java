package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.dto.ProductSaveDto;
import com.example.lets_shop_app.service.ProductService;
import com.example.lets_shop_app.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    private ProductService productService;

    public SellerServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<ProductSaveDto> addProducts(File file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line= reader.readLine()) != null){
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return productService.addProducts(lines);
    }
}