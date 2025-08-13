package com.example.lets_shop_app.dto;

import com.example.lets_shop_app.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

	private long id;
	private Product product;
	private BigDecimal totalProductPrice;
	private int productQuantity;
	
}
