package com.github.arlekinside.diploma.logic.service;

import com.github.arlekinside.diploma.data.enums.SecurityRoles;
import com.github.arlekinside.diploma.data.entity.Budget;
import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.data.repo.BudgetRepo;
import com.github.arlekinside.diploma.data.repo.UserRepo;
import com.github.arlekinside.diploma.logic.exception.NotFoundException;
import com.github.arlekinside.diploma.logic.exception.UserExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final BudgetRepo budgetRepo;

    public UserServiceImpl(UserRepo UserRepo, PasswordEncoder passwordEncoder, BudgetRepo budgetRepo) {
        this.userRepo = UserRepo;
        this.passwordEncoder = passwordEncoder;
        this.budgetRepo = budgetRepo;
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
        if (userRepo.findByUsername(username).isPresent()) {
            throw new UserExistsException();
        }

        var user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(securityRole)
                .build();

        user = userRepo.save(user);

        var budget = new Budget();
        budget.setUser(user);
        budgetRepo.save(budget);

        return user;
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public User read(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException(id, User.class));
    }
}
