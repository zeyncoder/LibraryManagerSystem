package com.devjoint.librarymanagersystem.mapper;

import com.devjoint.librarymanagersystem.model.dto.request.AuthorRequest;
import com.devjoint.librarymanagersystem.model.dto.response.AuthorResponse;
import com.devjoint.librarymanagersystem.model.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorRequest authorRequest);

    AuthorResponse toResponse(Author author);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    void updateEntityFromRequest(AuthorRequest authorRequest, @MappingTarget Author author);
}
