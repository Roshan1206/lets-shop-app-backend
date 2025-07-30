package com.example.lets_shop_app.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {


    /**
     * Generate new JWT token for user with the required details/claims
     *
     * @param extraClaims any extra claims to be added
     * @param userDetails UserDetails
     * @return token
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);


    /**
     * Generate new JWT token for user
     *
     * @param userDetails UserDetails
     * @return token
     */
    String generateToken(UserDetails userDetails);


    /**
     * Extract username from JWT token
     *
     * @param token User JWT token
     * @return username
     */
    String extractUsername(String token);


    /**
     * Validate JWT token.
     *
     * @param token User JWT token
     * @param userDetails UserDetails
     * @return true if token expired else false
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
