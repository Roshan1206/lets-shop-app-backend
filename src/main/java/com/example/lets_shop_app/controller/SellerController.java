package com.example.lets_shop_app.controller;

import com.example.lets_shop_app.dto.response.ProductSaveResponse;
import com.example.lets_shop_app.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    /**
     * Service class for Seller
     */
    private final SellerService sellerService;


    /**
     * Injecting required dependency
     */
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }


    /**
     * Adding products from csv file
     */
    @PostMapping("/addProducts")
    public ResponseEntity<List<ProductSaveResponse>> createNewProductsFromFile(@RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        if ("csv".equals(fileName.substring(fileName.lastIndexOf(".")))){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "File type not supported. Kindly use .csv file");
        }
        File newFile = new File(fileName);
        List<ProductSaveResponse> products = sellerService.addProducts(newFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(products);
    }
}
