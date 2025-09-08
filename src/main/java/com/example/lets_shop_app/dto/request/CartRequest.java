package com.example.lets_shop_app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {

	@NotNull
	public Long productId;

	@NotNull
	public Integer productQuantity;
}
