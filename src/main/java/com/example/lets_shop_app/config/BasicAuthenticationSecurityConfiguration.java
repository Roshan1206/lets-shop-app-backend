package com.example.lets_shop_app.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Configuration class for Actuator security
 *
 * @author Roshan
 */
@Configuration
@EnableWebSecurity
public class BasicAuthenticationSecurityConfiguration {

    /**
     * Retrieves username and password from external files
     */
    private final SecurityProperties securityProperties;


    /**
     * Constructor for injecting {@link SecurityProperties} dependency
     */
    public BasicAuthenticationSecurityConfiguration(SecurityProperties securityProperties){
        this.securityProperties = securityProperties;
    }


    /**
     * Configure {@link SecurityFilterChain} bean with Basic Authentication for handling actuator endpoints.
     * <p>
     *     This configuration includes:
     *     <ul>
     *         <li>Authenticating with basic authentication</li>
     *         <li>Grant access to "/health" and "/info" </li>
     *         <li>Uses http basic</li>
     *     </ul>
     * </p>
     *
     * @param http the {@link HttpSecurity} object provided by Spring Security
     * @return configured {@link SecurityFilterChain} instance
     * @throws Exception is the security configuration fails
     */
    @Bean
    @Order(1)
    public SecurityFilterChain basicAuthSecurityFilterChain(HttpSecurity http) throws Exception{
        http
                .securityMatcher(new AntPathRequestMatcher("/actuator/**"))
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers(EndpointRequest.to(HealthEndpoint.class), EndpointRequest.to(InfoEndpoint.class)).permitAll()
                                .anyRequest().hasRole("ACTUATOR"))
                .authenticationProvider(basicAuthAuthenticationProvider())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


    /**
     * Configure {@link UserDetailsService} bean for creating user to access actuator endpoints.
     * Creates an {@link InMemoryUserDetailsManager} user with username and password
     * defined in application.properties file having ACTUATOR role
     *
     * @return {@link InMemoryUserDetailsManager} user
     */
    @Bean
    public UserDetailsService basicAuthUserDetailsService(){
        UserDetails userDetails = User.withUsername(securityProperties.getUser().getName())
                .password("{noop}" + securityProperties.getUser().getPassword())
                .roles("ACTUATOR")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }


    /**
     * Provides a {@link AuthenticationProvider} method that uses DAO-based authentication mechanism for actuator endpoints
     * This sets the {@link UserDetailsService} into {@link DaoAuthenticationProvider}
     * We are not setting any {@link org.springframework.security.crypto.password.PasswordEncoder} so that it uses noop
     *
     * @return provider {@link AuthenticationProvider}
     */
    public AuthenticationProvider basicAuthAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(basicAuthUserDetailsService());
        return provider;
    }
}
