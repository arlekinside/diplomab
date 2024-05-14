package com.github.arlekinside.diploma.data.repo;

import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.data.entity.UserAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserJpaRepository<T extends UserAware, ID> extends JpaRepository<T, ID> {

    List<T> findAllByUser(User user);

    Optional<T> findByIdAndUser(ID id, User user);

    void deleteByIdAndUser(ID id, User user);

}
