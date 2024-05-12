package com.github.arlekinside.diploma.logic.service;

import com.github.arlekinside.diploma.data.SecurityRoles;
import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.data.repo.UserRepo;
import com.github.arlekinside.diploma.logic.exception.UserExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo UserRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = UserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String username, String password) throws UserExistsException {
        return register(username, password, SecurityRoles.USER);

    }

    @Override
    public User registerAdmin(String username, String password) throws UserExistsException {
        return register(username, password, SecurityRoles.ADMIN);
    }

    public User register(String username, String password, SecurityRoles securityRole) throws UserExistsException {
        if (userRepo.findByUsername(username) != null) {
            throw new UserExistsException();
        }

        var user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(securityRole)
                .build();

        return userRepo.save(user);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }
}
