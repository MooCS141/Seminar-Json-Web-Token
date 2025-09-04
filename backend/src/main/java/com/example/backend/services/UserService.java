package com.example.backend.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dtos.CreateUserRequest;
import com.example.backend.dtos.UpdateUserRequest;
import com.example.backend.exceptions.ConflictException;
import com.example.backend.exceptions.NotFoundException;
import com.example.backend.models.Role;
import com.example.backend.models.User;
import com.example.backend.repositories.RoleRepo;
import com.example.backend.repositories.UserRepo;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User Not Found."));
    }

    @Transactional
    public User createViewer(CreateUserRequest request) {
        if(userRepo.existsByEmail(request.email())){
            throw new ConflictException("Email '" + request.email() + "' is already registered");
        }
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        Role role = roleRepo.findById(2).orElseThrow(() -> new NotFoundException("Role Not Found."));
        user.setRole(role);
        return userRepo.save(user);
    }

    @Transactional
    public User createAdmin(CreateUserRequest request) {
        if(userRepo.existsByEmail(request.email())){
            throw new ConflictException("Email '" + request.email() + "' is already registered");
        }
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        Role role = roleRepo.findById(1).orElseThrow(() -> new NotFoundException("Role Not Found."));
        user.setRole(role);
        return userRepo.save(user);
    }

    @Transactional
    public User updateById(UUID id, UpdateUserRequest request) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found."));
        if (!user.getEmail().equals(request.email()) && userRepo.existsByEmail(request.email())) {
            throw new ConflictException("Email '" + request.email() + "' is already registered");
        }
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        Role role = roleRepo.findById(2).orElseThrow(() -> new NotFoundException("Role Not Found."));
        user.setRole(role);
        return userRepo.save(user);
    }

    @Transactional
    public void deleteById(UUID id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found."));
        userRepo.delete(user);
    }

}
