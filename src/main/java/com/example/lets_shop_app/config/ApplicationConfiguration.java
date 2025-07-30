package com.example.lets_shop_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.lets_shop_app.dao.UserRepository;

import lombok.RequiredArgsConstructor;

/**
* Configuration class for defining application-level beans related to authentication and user management.
* <p>
* This includes:
* <ul>
* 	<li>UserDetailsService for loading user-specific data during authentication </li>
* 	<li>PasswordEncoder bean using BCrypt </li>
* 	<li>AuthenticationManager and AuthenticationProvider for Spring Security </li>
* </ul>
* </p>
*
* @author Roshan
*/
@Configuration
public class ApplicationConfiguration {


	/**
	 * Repository responsible for handling User
	 */
	private final UserRepository userRepository;


	/**
	 * Constructor for injecting UserRepository dependency.
	 *
	 * @param userRepository repository used to fetch user data from the database
	 */
    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


	/**
	 * Provides a {@link UserDetailsService} bean used by Spring Security to fetch user details during authentication.
	 *
	 * @return {@link UserDetailsService} - if user found.
	 * @throws UsernameNotFoundException if email not found.
	 */
    @Bean
	public UserDetailsService userDetailsService() {
		return email -> userRepository.findByEmail(email)
										.orElseThrow(()->new UsernameNotFoundException("Email not found"));
	}


	/**
	 * Provides a {@link PasswordEncoder} bean using BCrypt hashing algorithm.
	 *
	 * @return {@link BCryptPasswordEncoder} instance
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	/**
	 * Provides the {@link AuthenticationManager} bean used for processing authentication requests.
	 *
	 * @param configuration {@link AuthenticationConfiguration} provided by Spring Security
	 * @return the default {@link AuthenticationManager}
	 * @throws Exception if authentication manager cannot be retrieved
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}


	/**
	 * Provides the {@link AuthenticationProvider} bean that uses DAO-based authentication mechanism.
	 * This sets the {@link UserDetailsService} and {@link PasswordEncoder} into a {@link DaoAuthenticationProvider}
	 *
	 * @return authenticationProvider {@link AuthenticationProvider}
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());;
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}
