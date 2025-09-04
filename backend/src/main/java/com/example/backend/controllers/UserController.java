package com.example.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dtos.UpdateUserRequest;
import com.example.backend.dtos.UserResponse;
import com.example.backend.mappers.UserMapper;
import com.example.backend.services.UserService;

import jakarta.validation.Valid;

@RequestMapping("/users")
@RestController
public class UserController {
    
    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userMapper.toListResponse(userService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userMapper.toResponse(userService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id,
                               @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userMapper.toResponse(userService.updateById(id, request)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.deleteById(id);
        ResponseEntity.noContent().build();
    }

}
