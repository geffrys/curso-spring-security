package com.platzi.pizza.service;

import com.platzi.pizza.persistence.entity.UserEntity;
import com.platzi.pizza.persistence.entity.UserRoleEntity;
import com.platzi.pizza.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findById(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        String[] roles = user.get().getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);
        return User.builder()
                .username(user.get().getUsername())
                .password(user.get().getPassword())
                .accountLocked(user.get().getLocked())
                .disabled(user.get().getDisabled())
                .roles(roles)
                .build();
    }
}
