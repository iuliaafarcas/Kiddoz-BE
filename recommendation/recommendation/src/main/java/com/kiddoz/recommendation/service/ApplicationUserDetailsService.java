package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.dto.ApplicationUserDto;
import com.kiddoz.recommendation.model.ApplicationUser;
import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.model.Roles;
import com.kiddoz.recommendation.model.Specialist;
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

    public ApplicationUserDto getApplicationUserByUsername(String username) {

        ApplicationUser user = repository.findApplicationUserByEmail(username);
        ApplicationUserDto userDto = new ApplicationUserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user instanceof Specialist ? "Specialist" : user instanceof Parent ? "Parent" : "");

        return userDto;
    }
}
