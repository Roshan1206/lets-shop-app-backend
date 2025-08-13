package com.example.lets_shop_app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUserResponse {

	private long id;
	private long productId;
	private String productName;
	private String productThumbnail;
	private BigDecimal productPrice;
	private int productQuantity;
	private BigDecimal totalPrice;
	private LocalDateTime createdDate;
}
