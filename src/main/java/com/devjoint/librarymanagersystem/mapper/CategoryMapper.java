package com.devjoint.librarymanagersystem.mapper;


import com.devjoint.librarymanagersystem.model.dto.request.CategoryRequest;
import com.devjoint.librarymanagersystem.model.dto.response.CategoryResponse;
import com.devjoint.librarymanagersystem.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);

}