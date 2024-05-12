package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.ws.config.security.user.UserDetailsAdapter;
import org.springframework.security.core.Authentication;

public abstract class AbstractCrudController<T> implements CrudController<T> {

    protected User getUser(Authentication auth) {
        return ((UserDetailsAdapter) auth.getPrincipal()).user();
    }

}
