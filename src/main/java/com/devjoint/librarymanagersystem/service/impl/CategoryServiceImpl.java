package com.devjoint.librarymanagersystem.service.impl;

import com.devjoint.librarymanagersystem.mapper.CategoryMapper;
import com.devjoint.librarymanagersystem.model.dto.request.CategoryRequest;
import com.devjoint.librarymanagersystem.model.dto.response.CategoryResponse;
import com.devjoint.librarymanagersystem.model.entity.Category;
import com.devjoint.librarymanagersystem.repository.CategoryRepository;
import com.devjoint.librarymanagersystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse create(CategoryRequest request) {

        Category category = categoryMapper.toEntity(request);

        return categoryMapper.toResponse(
                categoryRepository.save(category)
        );
    }

    @Override
    public List<CategoryResponse> getAll() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}