package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.ws.Utils;
import org.springframework.security.core.Authentication;

public abstract class AbstractCrudController<T> implements CrudController<T> {

    protected User getUser(Authentication auth) {
        return Utils.getUser(auth);
    }

}
