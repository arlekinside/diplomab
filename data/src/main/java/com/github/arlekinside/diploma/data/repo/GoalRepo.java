package com.github.arlekinside.diploma.data.repo;

import com.github.arlekinside.diploma.data.entity.Goal;
import com.github.arlekinside.diploma.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepo extends JpaRepository<Goal, Long> {

    List<Goal> findAllByUser(User user);

    Optional<Goal> findByIdAndUser(Long id, User user);

    void deleteByIdAndUser(Long id, User user);
}
