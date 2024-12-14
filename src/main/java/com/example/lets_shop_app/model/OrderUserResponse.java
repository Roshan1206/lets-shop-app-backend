package com.example.lets_shop_app.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUserResponse {

	private Long productId;
	private String productName;
	private String productThumbnail;
	private Double productPrice;
	private Integer productQuantity;
	private Double totalPrice;
	private Date createdDate;
}
