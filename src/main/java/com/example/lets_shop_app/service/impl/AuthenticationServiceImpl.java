package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.constant.Constants;
import com.example.lets_shop_app.dao.RoleRepository;
import com.example.lets_shop_app.entity.Role;
import com.example.lets_shop_app.service.AuthenticationService;
import com.example.lets_shop_app.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.dao.UserRepository;
import com.example.lets_shop_app.entity.User;
import com.example.lets_shop_app.dto.AuthenticateRequest;
import com.example.lets_shop_app.dto.AuthenticationResponse;
import com.example.lets_shop_app.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;


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
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;


	/**
	 * Create new user in server.
	 *
	 * @param registerRequest user details
	 * @param authority user role
	 * @return jwt token
	 */
	@Override
	public AuthenticationResponse register(RegisterRequest registerRequest, String authority) {
		User user = createNewUser(registerRequest, Constants.ROLE+authority);
		
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
		Set<Role> roles = new HashSet<>();

		Role role = roleRepository.findByName(authority).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, authority + " not found")
		);

		roles.add(role);
//		if(userRepository.count() == 0){
//			role = roleRepository.findByName(Constants.ROLE + Constants.ADMIN).orElseThrow(
//					() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ROLE + Constants.ADMIN + " not found")
//			);
//			roles.add(role);
//		}

		user.setFirstname(registerRequest.getFirstname());
		user.setLastname(registerRequest.getLastname());
		user.setEmail(registerRequest.getEmail());
		user.setRoles(roles);
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		return user;
	}

}
