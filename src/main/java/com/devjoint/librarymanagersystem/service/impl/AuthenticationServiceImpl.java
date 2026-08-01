package com.devjoint.librarymanagersystem.service.impl;

import com.devjoint.librarymanagersystem.model.dto.request.LoginRequest;
import com.devjoint.librarymanagersystem.model.dto.response.AuthResponse;
import com.devjoint.librarymanagersystem.model.entity.User;
import com.devjoint.librarymanagersystem.repository.UserRepository;
import com.devjoint.librarymanagersystem.service.AuthenticationService;
import com.devjoint.librarymanagersystem.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        String token = jwtService.generateToken(claims, user);

        return new AuthResponse(token);
    }
}