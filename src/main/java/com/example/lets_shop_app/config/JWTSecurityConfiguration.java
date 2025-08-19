package com.example.lets_shop_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.lets_shop_app.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * Configuration class for defining Application security beans
 * <p>
 *     This includes:
 *     <ul>
 *         <li>WebSecurityCustomizer bean to ignore specific urls</li>
 *         <li>SecurityFilterChain bean to secure application</li>
 *         <li>CorsConfiguration bean for external origins</li>
 *     </ul>
 * </p>
 *
 * @author Roshan
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JWTSecurityConfiguration {


	/**
	 * Filter responsible for intercepting and validating HTTP request.
	 */
	private final JwtAuthenticationFilter jwtAuthenticationFilter;


	/**
	 * Provider responsible for authenticating users.
	 */
	private final AuthenticationProvider authenticationProvider;


	/**
	 * Constructor for injecting {@link JwtAuthenticationFilter} and {@link AuthenticationProvider} dependency
	 *
	 * @param jwtAuthenticationFilter filter used for JWT authentication
	 * @param authenticationProvider provided by Spring security for authentication
	 */
	public JWTSecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.authenticationProvider = authenticationProvider;
	}


	/**
	 * Reading value from properties file for {@code configurationSource}
	 */
	@Value("${allowed.origin}")
	private String[] allowedOrigin;

	/**
	 * Swagger related urls that should be authorized for all users
	 */
	private final String[] SWAGGER_URLS = {"/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", "/docs"};

	/**
	 * Products related url that should be authorized to all users
	 */
	private final String[] PRODUCTS_URLS = {"/products/**", "/products", "/product-category", "/product-category/**"};


	/**
	 * Configures the Security filter chain for handling HTTP security in the application.
	 * <p>
	 *     This Configuration:
	 *     <ul>
	 *         <li>Enables CORS with default settings</li>
	 *         <li>Disables CSRF protection</li>
	 *         <li>Allows Public access to swagger, products, auth and error</li>
	 *         <li>Creates Stateless session</li>
	 *         <li>Requires authentication for all other endpoints</li>
	 *         <li>Register custom {@code authenticationProvider} and filter {@code jwtAuthenticationFilter}</li>
	 *     </ul>
	 * </p>
	 *
	 * @param http the {@link HttpSecurity} object provided by Spring Security
	 * @return configured {@link SecurityFilterChain} instance
	 * @throws Exception is the security configuration fails
	 */
	@Bean
	@Order(2)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.cors(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req ->
						req
								.requestMatchers(SWAGGER_URLS).permitAll()
								.requestMatchers(HttpMethod.GET, PRODUCTS_URLS).permitAll()
								.requestMatchers("/auth/**", "/error").permitAll()
								.anyRequest().authenticated())
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}


	/**
	 * Configures the CorsConfiguration for handling request for different origins.
	 * Origin {@code allowedOrigin} should be set externally
	 * Added necessary HTTP methods to function properly.
	 * Configures for all the request
	 *
	 * @return Configured {@link UrlBasedCorsConfigurationSource}
	 */
	@Bean
	public CorsConfigurationSource configurationSource(){
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(List.of(allowedOrigin));
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		corsConfiguration.setAllowedHeaders(List.of("*"));
		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
}
