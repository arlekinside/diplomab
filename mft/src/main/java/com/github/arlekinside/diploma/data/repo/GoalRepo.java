package com.github.arlekinside.diploma.data.repo;

import com.github.arlekinside.diploma.data.entity.Goal;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepo extends UserJpaRepository<Goal, Long> {

}
