package com.github.arlekinside.diploma.data.repo;

import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.data.entity.mf.MoneyFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoneyFlowRepo<T extends MoneyFlow> extends JpaRepository<T, Long> {

//    @Query("from MoneyFlow where user = :user")
    List<T> findAllByUser(User user);

    T findByIdAndUser(Long id, User user);

    void deleteByIdAndUser(Long id, User user);
}
