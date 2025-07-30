package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.UserInfoResponseDto;


/**
 * Methods to be implemented in {@link com.example.lets_shop_app.service.impl.UserServiceImpl} class
 *
 * @author Roshan
 */
public interface UserService {


    /**
     * Get user details of authenticated user
     *
     * @return User info
     */
    UserInfoResponseDto getUserInfo();


    /**
     * Delete authenticated user
     */
    void deleteUser();
}
