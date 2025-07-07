package com.example.lets_shop_app.util.impl;

import com.example.lets_shop_app.dao.UserRepository;
import com.example.lets_shop_app.entity.User;
import com.example.lets_shop_app.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtilImpl implements UserUtil {

    private final UserRepository userRepository;

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Email not found")
        );
    }

    /**
     * @return
     */
    @Override
    public String getAuthenticatedUserEmail() {
        return getAuthenticatedUser().getEmail();
    }
}
