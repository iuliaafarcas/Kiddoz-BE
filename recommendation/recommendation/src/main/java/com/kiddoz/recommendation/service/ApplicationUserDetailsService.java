package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.ApplicationUser;
import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.model.Roles;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {
    private final ApplicationUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final ApplicationUser user = repository.findApplicationUserByEmail(username);
        if (user == null) throw new UsernameNotFoundException("User does not exist!");

        return User.withUsername(user.getEmail()).password(user.getPassword())
                .authorities(user instanceof Parent ? Roles.USER_PARENT : Roles.USER_SPECIALIST).build();
    }

    public ApplicationUser getApplicationUserByUsername(String username) {
        return repository.findApplicationUserByEmail(username);
    }
}
