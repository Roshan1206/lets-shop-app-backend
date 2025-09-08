package com.example.lets_shop_app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	private String firstname;
	private String lastname;
	private String email;
	private String password;
}
