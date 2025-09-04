package com.example.backend.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.backend.dtos.RoleResponse;
import com.example.backend.dtos.UserResponse;
import com.example.backend.models.Role;
import com.example.backend.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    List<UserResponse> toListResponse(List<User> users);

    RoleResponse toRoleResponse(Role role);

}
