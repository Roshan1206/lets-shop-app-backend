package com.example.lets_shop_app.service;

import com.example.lets_shop_app.entity.User;

public interface AdminService {

    User viewUserDetails(String email);
    
    }