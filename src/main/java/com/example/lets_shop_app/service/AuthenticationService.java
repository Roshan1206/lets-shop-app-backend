package com.example.lets_shop_app.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.dao.UserRepository;
import com.example.lets_shop_app.entity.User;
import com.example.lets_shop_app.entity._enum.Role;
import com.example.lets_shop_app.model.AuthenticateRequest;
import com.example.lets_shop_app.model.AuthenticationResponse;
import com.example.lets_shop_app.model.RegisterRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	
	public AuthenticationResponse registerUser(RegisterRequest registerRequest) {
		User user = User.builder()
						.firstname(registerRequest.getFirstname())
						.lastname(registerRequest.getLastname())
						.email(registerRequest.getEmail())
						.password(passwordEncoder.encode(registerRequest.getPassword()))
						.role(Role.USER)
						.build();
		
		userRepository.save(user);		
		var jwtToken = jwtService.genrateToken(user);
		return AuthenticationResponse.builder()
										.token(jwtToken)
										.build();
		
	}
	
	public AuthenticationResponse authenticateUser(AuthenticateRequest authenticateRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticateRequest.getEmail(), authenticateRequest.getPassword()));
		
		User user = userRepository.findByEmail(authenticateRequest.getEmail()).orElseThrow();
		String jwtToken = jwtService.genrateToken(user);
//		System.out.println(user);
		
		return AuthenticationResponse.builder()
										.token(jwtToken)
										.build();
	}

}
