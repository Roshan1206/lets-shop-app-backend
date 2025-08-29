package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.dao.UserRepository;
import com.example.lets_shop_app.dto.UserInfoResponseDto;
import com.example.lets_shop_app.entity.User;
import com.example.lets_shop_app.service.UserService;
import com.example.lets_shop_app.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Service class for managing user
 *
 * @author Roshan
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    /**
     * Injecting {@link UserRepository} and {@link UserUtil}
     */
    private final UserRepository userRepository;
    private final UserUtil userUtil;


    /**
     * Get user details of authenticated user
     *
     * @return User info
     */
    @Override
    public UserInfoResponseDto getUserInfo() {
        User user = userUtil.getAuthenticatedUser();

        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto();
        userInfoResponseDto.setFirstName(user.getFirstname());
        userInfoResponseDto.setLastName(user.getLastname());
        userInfoResponseDto.setEmail(user.getEmail());
        return userInfoResponseDto;
    }


    /**
     * Delete authenticated user
     */
    @Override
    public void deleteUser() {
        User user = userUtil.getAuthenticatedUser();
        userRepository.delete(user);
    }
}
