package com.github.arlekinside.diploma.data.repo;

import com.github.arlekinside.diploma.data.entity.Saving;
import com.github.arlekinside.diploma.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingRepo extends JpaRepository<Saving, Long> {
    List<Saving> findAllByUser(User user);

    Optional<Saving> findByIdAndUser(Long id, User user);

    void deleteByIdAndUser(Long id, User user);
}
