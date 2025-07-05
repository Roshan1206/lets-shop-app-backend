package com.example.lets_shop_app.controller;

import com.example.lets_shop_app.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lets_shop_app.dto.AuthenticateRequest;
import com.example.lets_shop_app.dto.AuthenticationResponse;
import com.example.lets_shop_app.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	
	@PostMapping("/register/user")
	public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest registerRequest){
		AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest, "USER");
		return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
	}


	@PostMapping("/register/seller")
	public ResponseEntity<AuthenticationResponse> registerSeller(@RequestBody RegisterRequest registerRequest){
		AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest, "SELLER");
		return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
	}


	@PostMapping("/register/admin")
	public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest registerRequest){
		AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest, "ADMIN");
		return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
	}

	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest){
		AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticateRequest);
		return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
	}

}
