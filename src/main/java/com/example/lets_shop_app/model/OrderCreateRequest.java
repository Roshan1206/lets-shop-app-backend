package com.example.lets_shop_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

	private Long productId;
	private Integer totalQuantity;
}
