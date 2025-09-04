package com.example.backend.jwt;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .map(user -> User.builder()
                        .username(email)
                        .password(user.getPassword())
                        .roles(user.getRole().getName())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

}