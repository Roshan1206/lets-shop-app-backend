package com.example.lets_shop_app.controller;

import com.example.lets_shop_app.entity._enum.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lets_shop_app.dto.AuthenticateRequest;
import com.example.lets_shop_app.dto.AuthenticationResponse;
import com.example.lets_shop_app.dto.RegisterRequest;
import com.example.lets_shop_app.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	
	@PostMapping("/register/user")
	public ResponseEntity<AuthenticationResponse> registerUser(
			@RequestBody RegisterRequest registerRequest){
		return ResponseEntity.ok(
				authenticationService.registerUser(registerRequest, Role.USER));
	}


	@PostMapping("/register/seller")
	public ResponseEntity<AuthenticationResponse> registerSeller(
			@RequestBody RegisterRequest registerRequest){
		return ResponseEntity.ok(
				authenticationService.registerUser(registerRequest, Role.SELLER));
	}

	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticateRequest authenticateRequest){
		return ResponseEntity.ok(
				authenticationService.authenticateUser(authenticateRequest));
	}

}
