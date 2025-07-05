package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.AuthenticateRequest;
import com.example.lets_shop_app.dto.AuthenticationResponse;
import com.example.lets_shop_app.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest registerRequest, String authority);

    AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest);
}
