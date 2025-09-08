package com.example.lets_shop_app.util.impl;

import com.example.lets_shop_app.repository.UserRepository;
import com.example.lets_shop_app.entity.User;
import com.example.lets_shop_app.util.UserUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * Utility class for Authenticated User
 *
 * @author Roshan
 */
@Component
public class UserUtilImpl implements UserUtil {


    /**
     * Repository responsible for handling User
     */
    private final UserRepository userRepository;


    /**
     * Constructor for injecting {@link UserRepository} dependency
     */
    public UserUtilImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Fetches current authenticated user
     *
     * @return String user
     */
    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Email not found")
        );
    }

    /**
     * Fetches current authenticated user id
     *
     * @return long userId
     */
    @Override
    public long getUserId() {
        return getAuthenticatedUser().getId();
    }

    /**
     * Fetches current authenticated user email
     *
     * @return String userEmail
     */
    @Override
    public String getAuthenticatedUserEmail() {
        return getAuthenticatedUser().getEmail();
    }
}
