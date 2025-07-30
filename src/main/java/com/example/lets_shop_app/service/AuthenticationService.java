package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.AuthenticateRequest;
import com.example.lets_shop_app.dto.AuthenticationResponse;
import com.example.lets_shop_app.dto.RegisterRequest;

/**
 * Defining methods for authentication related services.
 *
 * @author Roshan
 */
public interface AuthenticationService {


    /**
     * Create new user in server.
     *
     * @param registerRequest user details
     * @param authority user role
     * @return jwt token
     */
    AuthenticationResponse register(RegisterRequest registerRequest, String authority);


    /**
     * Login the user in the server.
     *
     * @param authenticateRequest user credentials.
     * @return jwt token
     */
    AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest);
}
