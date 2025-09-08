package com.example.lets_shop_app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String token;
}
