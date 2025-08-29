package com.example.lets_shop_app.controller;

import com.example.lets_shop_app.service.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }
}
