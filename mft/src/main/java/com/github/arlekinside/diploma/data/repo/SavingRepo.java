package com.github.arlekinside.diploma.data.repo;

import com.github.arlekinside.diploma.data.entity.Saving;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingRepo extends UserJpaRepository<Saving, Long> {

}
