package com.github.arlekinside.diploma.ws.app.service.impl;

import com.github.arlekinside.diploma.data.repo.SUserRepo;
import com.github.arlekinside.diploma.ws.app.entity.UserDetailsAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final SUserRepo userRepo;

    public UserDetailsService(SUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found for: [%s]".formatted(username));
        }

        return new UserDetailsAdapter(user);
    }
}
