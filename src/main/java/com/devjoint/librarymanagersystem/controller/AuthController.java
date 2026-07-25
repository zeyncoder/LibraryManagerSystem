package com.devjoint.librarymanagersystem.controller;


import com.devjoint.librarymanagersystem.model.dto.request.LoginRequest;
import com.devjoint.librarymanagersystem.model.dto.request.RegisterRequest;
import com.devjoint.librarymanagersystem.model.dto.response.AuthResponse;
import com.devjoint.librarymanagersystem.model.dto.response.UserResponse;
import com.devjoint.librarymanagersystem.service.AuthenticationService;
import com.devjoint.librarymanagersystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {

        return userService.register(request);

    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        return authenticationService.login(request);

    }
}