package com.devjoint.librarymanagersystem.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request object for creating a category")
public class CategoryRequest {

    @Schema(
            description = "Category name",
            example = "Programming"
    )
    @NotBlank(message = "Category name is required")
    private String name;
}