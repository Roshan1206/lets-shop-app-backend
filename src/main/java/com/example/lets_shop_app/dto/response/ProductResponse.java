package com.example.lets_shop_app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private long id;
    private String name;
    private String description;
    private String thumbnail;
    private BigDecimal price;
    private long stock;
    private String brand;
    private String category;
}
