package com.example.lets_shop_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsResponse {
	
	private Long productId;
	private String productName;
	private String productThumbnail;
	private Integer productQuantity;
	private Double productPrice;
	private Double totalProductPrice;
	
}
