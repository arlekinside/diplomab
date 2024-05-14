package com.github.arlekinside.diploma.logic.service;

import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.logic.exception.UserExistsException;

public interface UserService {

    User registerUser(String username, String password) throws UserExistsException;

    User registerAdmin(String username, String password) throws UserExistsException;

    void save(User user);
}
