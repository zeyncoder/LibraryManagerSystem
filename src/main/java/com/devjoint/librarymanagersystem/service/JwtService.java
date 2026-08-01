package com.devjoint.librarymanagersystem.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

public interface JwtService {
    String generateToken(Map<String, Object> claims, UserDetails userDetails);

    String extractUsername(String token);

    boolean isTokenValid(String token, String username);
    Date extractExpiration(String token);
}

