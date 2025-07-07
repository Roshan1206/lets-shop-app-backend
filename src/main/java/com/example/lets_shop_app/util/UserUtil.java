package com.example.lets_shop_app.util;

import com.example.lets_shop_app.entity.User;

public interface UserUtil {

    String getAuthenticatedUserEmail();
    User getAuthenticatedUser();
}
