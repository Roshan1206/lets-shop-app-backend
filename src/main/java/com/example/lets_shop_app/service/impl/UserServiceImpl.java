package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.dto.request.UpdatePasswordRequest;
import com.example.lets_shop_app.repository.UserRepository;
import com.example.lets_shop_app.dto.response.UserInfoResponseDto;
import com.example.lets_shop_app.entity.User;
import com.example.lets_shop_app.service.UserService;
import com.example.lets_shop_app.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


/**
 * Service class for managing user
 *
 * @author Roshan
 */
@Service
public class UserServiceImpl implements UserService {


    /**
     * Repository responsible for managing users
     */
    private final UserRepository userRepository;


    /**
     * Utility class for user
     */
    private final UserUtil userUtil;


    /**
     * For encoding password
     */
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, UserUtil userUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userUtil = userUtil;
    }


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
     * Update password for current authenticated user
     *
     * @param request passwords
     */
    @Override
    public void updatePassword(UpdatePasswordRequest request) {
        User authenticatedUser = userUtil.getAuthenticatedUser();
        String savedPassword = authenticatedUser.getPassword();

        if (!passwordEncoder.matches(request.getCurrentPassword(), savedPassword)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }

        if (request.getCurrentPassword().equals(request.getNewPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current and new password cannot be same");
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New and Confirm New password do not match");
        }

        authenticatedUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(authenticatedUser);
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
