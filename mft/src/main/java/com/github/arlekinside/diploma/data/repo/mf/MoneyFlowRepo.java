package com.github.arlekinside.diploma.data.repo.mf;

import com.github.arlekinside.diploma.data.entity.mf.MoneyFlow;
import com.github.arlekinside.diploma.data.repo.UserJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MoneyFlowRepo<T extends MoneyFlow> extends UserJpaRepository<T, Long> {

}
