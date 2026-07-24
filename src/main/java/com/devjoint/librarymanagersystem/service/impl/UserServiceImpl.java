package com.devjoint.librarymanagersystem.service.impl;


import com.devjoint.librarymanagersystem.mapper.UserMapper;
import com.devjoint.librarymanagersystem.model.dto.request.RegisterRequest;
import com.devjoint.librarymanagersystem.model.dto.response.UserResponse;
import com.devjoint.librarymanagersystem.model.entity.Role;
import com.devjoint.librarymanagersystem.model.entity.User;
import com.devjoint.librarymanagersystem.repository.UserRepository;
import com.devjoint.librarymanagersystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        user = userRepository.save(user);

        return userMapper.toResponse(user);
    }
}