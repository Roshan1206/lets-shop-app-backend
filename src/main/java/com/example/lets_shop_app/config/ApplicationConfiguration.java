package com.example.lets_shop_app.config;

import com.example.lets_shop_app.provider.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
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
	 * @param userDetailsService {@link UserDetailsService} provided by Spring Security
	 * @param passwordEncoder {@link PasswordEncoder} provided by Spring Security
	 * @return the {@link CustomAuthenticationProvider} to be used in {@link AuthenticationManager}
	 * @throws Exception if authentication manager cannot be retrieved
	 */
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
		return new ProviderManager(new CustomAuthenticationProvider(passwordEncoder, userDetailsService));
	}
}
