package com.example.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    
    @NotBlank(message = "Email is required.")
    @Size(max = 100, message = "Email must not exceed 100 characters.")
    @Email(message = "Invalid email format.")
    String email,

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Size(max = 50, message = "Password must not exceed 50 characters.")
    String password

) {

}
