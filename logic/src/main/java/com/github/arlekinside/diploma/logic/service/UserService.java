package com.github.arlekinside.diploma.logic.service;

import com.github.arlekinside.diploma.data.entity.SUser;
import com.github.arlekinside.diploma.logic.exception.UserExistsException;

public interface UserService {

    SUser registerUser(String username, String password) throws UserExistsException;

    SUser registerAdmin(String username, String password) throws UserExistsException;

    void save(SUser user);
}
