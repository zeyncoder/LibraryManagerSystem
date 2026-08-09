package com.devjoint.librarymanagersystem.controller;

import com.devjoint.librarymanagersystem.model.dto.request.CategoryRequest;
import com.devjoint.librarymanagersystem.model.dto.response.CategoryResponse;
import com.devjoint.librarymanagersystem.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(
        name = "Category Controller",
        description = "Operations related to categories"
)
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(
            summary = "Create a new category",
            description = "Creates a new category using the provided category information"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<CategoryResponse> create(
            @RequestBody @Valid CategoryRequest request) {

        return new ResponseEntity<>(
                categoryService.create(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Retrieves all available categories"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<List<CategoryResponse>> getAll() {

        return ResponseEntity.ok(
                categoryService.getAll()
        );
    }
}