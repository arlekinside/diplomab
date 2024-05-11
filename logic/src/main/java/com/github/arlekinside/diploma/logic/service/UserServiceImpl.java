package com.github.arlekinside.diploma.logic.service;

import com.github.arlekinside.diploma.data.entity.SUser;
import com.github.arlekinside.diploma.data.entity.SecurityRoles;
import com.github.arlekinside.diploma.data.repo.SUserRepo;
import com.github.arlekinside.diploma.logic.exception.UserExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class UserServiceImpl implements UserService {

    private final SUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(SUserRepo SUserRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = SUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SUser registerUser(String username, String password) throws UserExistsException {
        return register(username, password, SecurityRoles.USER);

    }

    @Override
    public SUser registerAdmin(String username, String password) throws UserExistsException {
        return register(username, password, SecurityRoles.ADMIN);
    }

    public SUser register(String username, String password, SecurityRoles securityRole) throws UserExistsException {
        if (userRepo.findByUsername(username) != null) {
            throw new UserExistsException();
        }

        var user = SUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(securityRole)
                .dateCreated(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()))
                .build();

        return userRepo.save(user);
    }

    @Override
    public void save(SUser user) {
        userRepo.save(user);
    }
}
