package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.constant.Constants;
import com.example.lets_shop_app.dto.response.UserSignUpResponse;
import com.example.lets_shop_app.repository.RoleRepository;
import com.example.lets_shop_app.entity.Role;
import com.example.lets_shop_app.service.AuthenticationService;
import com.example.lets_shop_app.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.repository.UserRepository;
import com.example.lets_shop_app.entity.User;
import com.example.lets_shop_app.dto.request.AuthenticateRequest;
import com.example.lets_shop_app.dto.response.UserLoginResponse;
import com.example.lets_shop_app.dto.request.RegisterRequest;

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
public class AuthenticationServiceImpl implements AuthenticationService {


	/**
	 * Service interface for authenticating user
	 */
	private final AuthenticationManager authenticationManager;


	/**
	 * Service interface for JWT related operations
	 */
	private final JwtService jwtService;


	/**
	 * Service interface for password encoding
	 */
	private final PasswordEncoder passwordEncoder;


	/**
	 * Repository responsible for managing user roles
	 */
	private final RoleRepository roleRepository;


	/**
	 * Repository responsible for managing users
	 */
	private final UserRepository userRepository;


	/**
	 * Injecting required dependency for this service class
	 */
	public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
									 JwtService jwtService,
									 PasswordEncoder passwordEncoder,
									 RoleRepository roleRepository,
									 UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}


	/**
	 * Create new user in server.
	 *
	 * @param registerRequest user details
	 * @param authority user role
	 * @return user info with jwt token
	 */
	@Override
	public UserSignUpResponse register(RegisterRequest registerRequest, String authority) {
		User user = createNewUser(registerRequest, Constants.ROLE+authority);
		
		User savedUser = userRepository.save(user);
		String jwtToken = jwtService.generateToken(savedUser);

		return createUserSignUpResponse(savedUser, jwtToken);
	}


	/**
	 * Login the user in the server.
	 *
	 * @param authenticateRequest user credentials.
	 * @return jwt token
	 */
	@Override
	public UserLoginResponse authenticate(AuthenticateRequest authenticateRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticateRequest.getEmail(), authenticateRequest.getPassword()));
		
		User user = userRepository.findByEmail(authenticateRequest.getEmail()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username or password")
		);
		String jwtToken = jwtService.generateToken(user);

		return new UserLoginResponse(jwtToken);
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

		user.setFirstname(registerRequest.getFirstname());
		user.setLastname(registerRequest.getLastname());
		user.setEmail(registerRequest.getEmail());
		user.setRoles(roles);
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		return user;
	}


	/**
	 * Create response for newly registered user
	 * @param user user details
	 * @param token jwt token
	 * @return user response
	 */
	private UserSignUpResponse createUserSignUpResponse(User user, String token) {
		UserSignUpResponse response = new UserSignUpResponse();
		response.setEmail(user.getEmail());
		response.setFirstName(user.getFirstname());
		response.setLastName(user.getLastname());
		response.setToken(token);
		return response;
	}

}
