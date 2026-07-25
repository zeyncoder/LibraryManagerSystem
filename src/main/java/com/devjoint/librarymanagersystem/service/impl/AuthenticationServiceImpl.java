package com.devjoint.librarymanagersystem.service.impl;

import com.devjoint.librarymanagersystem.model.dto.request.LoginRequest;
import com.devjoint.librarymanagersystem.model.dto.response.AuthResponse;
import com.devjoint.librarymanagersystem.service.AuthenticationService;
import com.devjoint.librarymanagersystem.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getUsername());

        return new AuthResponse(token);
    }
}