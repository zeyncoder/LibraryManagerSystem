package com.devjoint.librarymanagersystem.mapper;


import com.devjoint.librarymanagersystem.model.dto.request.RegisterRequest;
import com.devjoint.librarymanagersystem.model.dto.response.UserResponse;
import com.devjoint.librarymanagersystem.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);
}