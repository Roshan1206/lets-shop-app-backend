package com.example.lets_shop_app.dto;

import com.example.lets_shop_app.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Authority> authorities;
}
