package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.constant.Constants;
import com.example.lets_shop_app.entity.Authority;
import com.example.lets_shop_app.service.AuthenticationService;
import com.example.lets_shop_app.service.JwtService;
import com.example.lets_shop_app.util.UserUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.dao.UserRepository;
import com.example.lets_shop_app.entity.User;
import com.example.lets_shop_app.dto.AuthenticateRequest;
import com.example.lets_shop_app.dto.AuthenticationResponse;
import com.example.lets_shop_app.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final UserUtil userUtil;

	@Override
	public AuthenticationResponse register(RegisterRequest registerRequest, String authority) {
		User user = createNewUser(registerRequest, authority);
		
		userRepository.save(user);		
		String jwtToken = jwtService.generateToken(user);
		return new AuthenticationResponse(jwtToken);
	}


	@Override
	public AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticateRequest.getEmail(), authenticateRequest.getPassword()));
		
		User user = userUtil.getAuthenticatedUser();
		String jwtToken = jwtService.generateToken(user);

		return new AuthenticationResponse(jwtToken);
	}


	private User createNewUser(RegisterRequest registerRequest, String authority){
		User user = new User();
		List<Authority> authorities = new ArrayList<>();

		authorities.add(new Authority("ROLE_" + authority));
		if(userRepository.count() == 0){
			authorities.add(new Authority("ROLE_" + Constants.ADMIN));
		}

		user.setFirstname(registerRequest.getFirstname());
		user.setLastname(registerRequest.getLastname());
		user.setEmail(registerRequest.getEmail());
		user.setAuthorities(authorities);
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		return user;
	}

}
