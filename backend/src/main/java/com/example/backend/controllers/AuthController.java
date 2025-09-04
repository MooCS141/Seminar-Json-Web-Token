package com.example.backend.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dtos.CreateUserRequest;
import com.example.backend.dtos.LoginRequest;
import com.example.backend.dtos.TokenResponse;
import com.example.backend.jwt.JwtService;
import com.example.backend.mappers.UserMapper;
import com.example.backend.models.User;
import com.example.backend.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/auth")
@RestController
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager,
            UserMapper userMapper) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    @PostMapping("/register/viewer")
    public ResponseEntity<?> createViewer(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createViewer(request);
        URI location = URI.create("/users/"+user.getId());
        return ResponseEntity.created(location).body(userMapper.toResponse(user));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createAdmin(request);
        URI location = URI.create("/users/"+user.getId());
        return ResponseEntity.created(location).body(userMapper.toResponse(user));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        if(authentication.isAuthenticated()) {
            return ResponseEntity.ok(new TokenResponse(jwtService.generateToken(authentication.getName())));
        }
        throw new UsernameNotFoundException("Invalid user request!");
    }

}
