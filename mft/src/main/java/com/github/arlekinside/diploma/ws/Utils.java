package com.github.arlekinside.diploma.ws;

import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.ws.config.security.user.UserDetailsAdapter;
import org.springframework.security.core.Authentication;

public class Utils {

    public static <T> T nvl(T input, T def) {
        if (input == null) {
            return def;
        }
        return input;
    }

    public static User getUser(Authentication auth) {
        return ((UserDetailsAdapter) auth.getPrincipal()).user();
    }

}
