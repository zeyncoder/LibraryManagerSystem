package com.devjoint.librarymanagersystem.model.dto.response;


import com.devjoint.librarymanagersystem.model.entity.Role;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
}