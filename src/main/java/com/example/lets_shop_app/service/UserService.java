package com.example.lets_shop_app.service;

import com.example.lets_shop_app.dto.UserInfoResponseDto;

public interface UserService {
    UserInfoResponseDto getUserInfo();
    void deleteUser();
}
