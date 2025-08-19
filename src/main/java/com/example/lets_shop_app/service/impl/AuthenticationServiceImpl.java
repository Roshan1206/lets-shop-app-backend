package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.constant.Constants;
import com.example.lets_shop_app.entity.Authority;
import com.example.lets_shop_app.service.AuthenticationService;
import com.example.lets_shop_app.service.JwtService;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


/**
 * Service class for Authentication related operations
 *
 * @author Roshan
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


	/**
	 * Injecting {@link UserRepository}, {@link JwtService},
	 * {@link AuthenticationManager}, {@link PasswordEncoder}
	 * using constructor injection through lombok
	 */
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;


	/**
	 * Create new user in server.
	 *
	 * @param registerRequest user details
	 * @param authority user role
	 * @return jwt token
	 */
	@Override
	public AuthenticationResponse register(RegisterRequest registerRequest, String authority) {
		User user = createNewUser(registerRequest, authority);
		
		userRepository.save(user);		
		String jwtToken = jwtService.generateToken(user);
		return new AuthenticationResponse(jwtToken);
	}


	/**
	 * Login the user in the server.
	 *
	 * @param authenticateRequest user credentials.
	 * @return jwt token
	 */
	@Override
	public AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticateRequest.getEmail(), authenticateRequest.getPassword()));
		
		User user = userRepository.findByEmail(authenticateRequest.getEmail()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username or password")
		);
		String jwtToken = jwtService.generateToken(user);

		return new AuthenticationResponse(jwtToken);
	}


	/**
	 * Create new user object
	 *
	 * @param registerRequest user details
	 * @param authority User role
	 * @return new user
	 */
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
