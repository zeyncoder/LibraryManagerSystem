package com.devjoint.librarymanagersystem.service;

import java.util.Date;

public interface JwtService {
    String generateToken(String username);

    String extractUsername(String token);

    boolean isTokenValid(String token, String username);
    Date extractExpiration(String token);
}

