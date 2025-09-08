package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.response.ProductSaveResponse;

import java.io.File;
import java.util.List;

public interface SellerService {

    List<ProductSaveResponse> addProducts(File file);
}
