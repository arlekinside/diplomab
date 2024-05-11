package com.github.arlekinside.diploma.data.repo;

import com.github.arlekinside.diploma.data.entity.SUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SUserRepo extends JpaRepository<SUser, Long> {

    SUser findByUsername(String username);

}
