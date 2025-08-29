package com.example.lets_shop_app.dto;

import com.example.lets_shop_app.entity.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private long id;
    private String name;
    private String description;
    private String thumbnail;
    private BigDecimal price;
    private long stock;
    private String brand;
    private String category;
}
