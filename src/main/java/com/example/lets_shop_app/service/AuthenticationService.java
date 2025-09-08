package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.request.AuthenticateRequest;
import com.example.lets_shop_app.dto.response.UserLoginResponse;
import com.example.lets_shop_app.dto.request.RegisterRequest;
import com.example.lets_shop_app.dto.response.UserSignUpResponse;

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
    UserSignUpResponse register(RegisterRequest registerRequest, String authority);


    /**
     * Login the user in the server.
     *
     * @param authenticateRequest user credentials.
     * @return jwt token
     */
    UserLoginResponse authenticate(AuthenticateRequest authenticateRequest);
}
