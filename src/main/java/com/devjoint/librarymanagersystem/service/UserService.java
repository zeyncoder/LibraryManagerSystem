package com.devjoint.librarymanagersystem.service;


import com.devjoint.librarymanagersystem.model.dto.request.RegisterRequest;
import com.devjoint.librarymanagersystem.model.dto.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);


}