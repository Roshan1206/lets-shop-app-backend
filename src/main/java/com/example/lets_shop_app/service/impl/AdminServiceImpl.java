package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.dao.UserRepository;
import com.example.lets_shop_app.entity.User;
import com.example.lets_shop_app.service.AdminService;
import com.example.lets_shop_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;
    private UserService userService;

    public AdminServiceImpl(UserService userService, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Override
    public User viewUserDetails(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find requested user with given email id : " + email)
        );
    }
}