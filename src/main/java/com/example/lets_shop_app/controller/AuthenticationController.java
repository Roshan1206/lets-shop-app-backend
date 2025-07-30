package com.example.lets_shop_app.controller;

import com.example.lets_shop_app.constant.Constants;
import com.example.lets_shop_app.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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


/**
 * Controller class for Authentication and Registration
 *
 * @author Roshan
 */
@Tag(name = "Authentication Endpoints", description = "Operational REST API endpoints related to Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {


	/**
	 * Injecting Service class for authentication operations using lombok
	 */
	private final AuthenticationService authenticationService;

	/**
	 * Creating user profile with USER role
	 *
	 * @param registerRequest details for creating user
	 * @return AuthenticationResponse/jwt token
	 */
	@Operation(
			summary = "Create user account",
			description = "Create user account for shopping",
			responses = {
					@ApiResponse(responseCode = "201", description = "User account created successfully"),
					@ApiResponse(responseCode = "400", description = Constants.EMAIL_ALREADY_EXIST),
					@ApiResponse(responseCode = "404", description = Constants.USER_NOT_FOUND),
					@ApiResponse(responseCode = "500", description = Constants.INTERNAL_SERVER_ERROR)
			}
	)
	@PostMapping("/register/user")
	public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest registerRequest){
		AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest, Constants.USER);
		return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
	}


	/**
	 * Creating user profile with SELLER role
	 *
	 * @param registerRequest details for creating user
	 * @return AuthenticationResponse/jwt token
	 */
	@Operation(
			summary = "Create seller account",
			description = "Create seller accounts for merchants",
			responses = {
					@ApiResponse(responseCode = "201", description = "Seller account created successfully"),
					@ApiResponse(responseCode = "400", description = Constants.EMAIL_ALREADY_EXIST),
					@ApiResponse(responseCode = "500", description = Constants.INTERNAL_SERVER_ERROR)
			}
	)
	@PostMapping("/register/seller")
	public ResponseEntity<AuthenticationResponse> registerSeller(@RequestBody RegisterRequest registerRequest){
		AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest, Constants.SELLER);
		return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
	}


	/**
	 * Creating user profile with ADMIN role
	 *
	 * @param registerRequest details for creating user
	 * @return AuthenticationResponse/jwt token
	 */
	@Operation(
			summary = "Create admin account",
			description = "Create admin account for internal employees",
			responses = {
					@ApiResponse(responseCode = "201", description = "Admin account created successfully"),
					@ApiResponse(responseCode = "400", description = Constants.EMAIL_ALREADY_EXIST),
					@ApiResponse(responseCode = "500", description = Constants.INTERNAL_SERVER_ERROR)
			}
	)
	@PostMapping("/register/admin")
	public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest registerRequest){
		AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest, Constants.ADMIN);
		return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
	}


	/**
	 * Sign in the server
	 *
	 * @param authenticateRequest User credentials
	 * @return jwt token
	 */
	@Operation(
			summary = "Sign in",
			description = "Sign in to your account",
			responses = {
					@ApiResponse(responseCode = "200", description = "Sign in successfully"),
					@ApiResponse(responseCode = "404", description = Constants.USER_NOT_FOUND),
					@ApiResponse(responseCode = "500", description = Constants.INTERNAL_SERVER_ERROR)
			}
	)
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest){
		AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticateRequest);
		return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
	}

}
