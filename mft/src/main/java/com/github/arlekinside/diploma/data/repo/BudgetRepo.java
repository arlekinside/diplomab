package com.github.arlekinside.diploma.data.repo;

import com.github.arlekinside.diploma.data.entity.Budget;
import com.github.arlekinside.diploma.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepo extends JpaRepository<Budget, Long> {
    Budget findByUser(User user);
}
