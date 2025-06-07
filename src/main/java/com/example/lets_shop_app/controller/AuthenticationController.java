package com.example.lets_shop_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lets_shop_app.model.AuthenticateRequest;
import com.example.lets_shop_app.model.AuthenticationResponse;
import com.example.lets_shop_app.model.RegisterRequest;
import com.example.lets_shop_app.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody RegisterRequest registerRequest){
		return ResponseEntity.ok(
				authenticationService.registerUser(registerRequest));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticateRequest authenticateRequest){
		return ResponseEntity.ok(
				authenticationService.authenticateUser(authenticateRequest));
	}

}
