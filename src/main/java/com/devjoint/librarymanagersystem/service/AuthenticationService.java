package com.devjoint.librarymanagersystem.service;

import com.devjoint.librarymanagersystem.model.dto.request.LoginRequest;
import com.devjoint.librarymanagersystem.model.dto.response.AuthResponse;

public interface AuthenticationService {
    AuthResponse login(LoginRequest request);
}
