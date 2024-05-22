package com.github.arlekinside.diploma.data.repo;

import com.github.arlekinside.diploma.data.entity.Saving;
import com.github.arlekinside.diploma.data.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingRepo extends UserJpaRepository<Saving, Long> {

    @Query("select s from Saving s where s.finished is not true and s.user = :user")
    List<Saving> findAllActiveByUser(User user);
}
