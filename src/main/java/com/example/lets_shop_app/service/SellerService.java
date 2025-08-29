package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.ProductSaveDto;

import java.io.File;
import java.util.List;

public interface SellerService {

    List<ProductSaveDto> addProducts(File file);
}
