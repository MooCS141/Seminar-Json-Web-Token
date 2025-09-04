package com.example.backend.dtos;

import java.util.UUID;

public record UserResponse(
    UUID id,
    String email,
    RoleResponse role
) {

}
