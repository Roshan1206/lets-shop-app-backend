package com.example.lets_shop_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

	private Long id;
	private Integer productId;
	private String productName;
	private Double productPrice;
	private String productThumbnail;
	private Double totalProductPrice;
	private Integer productQuantity;
	
}
