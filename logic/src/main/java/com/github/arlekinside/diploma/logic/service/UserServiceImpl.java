package com.github.arlekinside.diploma.logic.service;

import com.github.arlekinside.diploma.data.RecurringCycle;
import com.github.arlekinside.diploma.data.entity.*;
import com.github.arlekinside.diploma.data.SecurityRoles;
import com.github.arlekinside.diploma.data.entity.finance.*;
import com.github.arlekinside.diploma.data.repo.UserRepo;
import com.github.arlekinside.diploma.logic.exception.UserExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

        var expense = new Expense();
        expense.setMoney(new Money(10));
        expense.setUser(user);
        var income = new Income();
        income.setMoney(new Money(5));
        income.setUser(user);
        var recurringIncome = new RecurringIncome();
        recurringIncome.setMoney(new Money(5));
        recurringIncome.setUser(user);
        recurringIncome.setCycle(RecurringCycle.DAILY);
        var recurringExpense = new RecurringExpense();
        recurringExpense.setMoney(new Money(5));
        recurringExpense.setUser(user);
        recurringExpense.setCycle(RecurringCycle.DAILY);

        var moneyData = user.getMoneyData();
        moneyData.getExpenses().add(expense);
        moneyData.getIncomes().add(income);
        moneyData.getRecurringExpenses().add(recurringExpense);
        moneyData.getRecurringIncomes().add(recurringIncome);

        return userRepo.save(user);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }
}
