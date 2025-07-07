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

@Configuration
@EnableWebSecurity
public class BasicAuthenticationSecurityConfiguration {

    private final SecurityProperties securityProperties;

    public BasicAuthenticationSecurityConfiguration(SecurityProperties securityProperties){
        this.securityProperties = securityProperties;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain basicAuthSecurityFilterChain(HttpSecurity http) throws Exception{
        http
                .securityMatcher("/actuator/**")
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers(EndpointRequest.to(HealthEndpoint.class), EndpointRequest.to(InfoEndpoint.class)).permitAll()
                                .anyRequest().hasRole("ACTUATOR"))
                .authenticationProvider(basicAuthAuthenticationProvider())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService basicAuthUserDetailsService(){
        UserDetails userDetails = User.withUsername(securityProperties.getUser().getName())
                .password("{noop}password")
                .roles("ACTUATOR")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

//    @Bean
    public AuthenticationProvider basicAuthAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(basicAuthUserDetailsService());
        return provider;
    }
}
