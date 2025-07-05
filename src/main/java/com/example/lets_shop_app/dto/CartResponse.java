package com.example.lets_shop_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

	private long id;
	private long productId;
	private String productName;
	private Double productPrice;
	private String productThumbnail;
	private Double totalProductPrice;
	private int productQuantity;
	
}
