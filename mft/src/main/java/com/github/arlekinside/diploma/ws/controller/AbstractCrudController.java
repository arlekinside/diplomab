package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.ws.Utils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;

public abstract class AbstractCrudController<T> implements CrudController<T> {

    protected User getUser(Authentication auth) {
        return Utils.getUser(auth);
    }
    protected User getUser(Principal auth) {
        return Utils.getUser((Authentication) auth);
    }

}
