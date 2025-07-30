package com.example.lets_shop_app.util;

import com.example.lets_shop_app.entity.User;


/**
 * Utility class for Authenticated User <br>
 * See also {@link com.example.lets_shop_app.util.impl.UserUtilImpl} for method implementations.
 *
 * @author Roshan
 */
public interface UserUtil {

    /**
     * Fetches current authenticated user
     *
     * @return String user
     */
    String getAuthenticatedUserEmail();

    /**
     * Fetches current authenticated user email
     *
     * @return String userEmail
     */
    User getAuthenticatedUser();
}
