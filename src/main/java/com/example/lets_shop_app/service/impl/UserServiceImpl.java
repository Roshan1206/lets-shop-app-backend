package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.dao.UserRepository;
import com.example.lets_shop_app.dto.UserInfoResponseDto;
import com.example.lets_shop_app.entity.Authority;
import com.example.lets_shop_app.entity.User;
import com.example.lets_shop_app.service.UserService;
import com.example.lets_shop_app.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @Override
    public UserInfoResponseDto getUserInfo() {
        User user = userUtil.getAuthenticatedUser();

        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto();
        userInfoResponseDto.setId(user.getId());
        userInfoResponseDto.setFirstName(user.getFirstname());
        userInfoResponseDto.setLastName(user.getLastname());
        userInfoResponseDto.setEmail(user.getEmail());
        userInfoResponseDto.setAuthorities(user.getAuthorities().stream().map(authority -> (Authority) authority).toList());
        return userInfoResponseDto;
    }

    @Override
    public void deleteUser() {
        User user = userUtil.getAuthenticatedUser();
        userRepository.delete(user);
    }
}
