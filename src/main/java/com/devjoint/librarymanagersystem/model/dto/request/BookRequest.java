package com.devjoint.librarymanagersystem.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating or updating a book")
public class BookRequest {

    @Schema(
            description = "Book title",
            example = "Clean Code"
    )
    @NotBlank(message = "Book title cannot be empty")
    private String title;

    @Schema(
            description = "Book ISBN number",
            example = "9780132350884"
    )
    @NotBlank(message = "Book ISBN cannot be empty")
    private String isbn;

    @Schema(
            description = "Book price",
            example = "45.99"
    )
    @NotNull(message = "Book price cannot be null")
    @Positive(message = "Book price must be positive")
    private Double price;

    @Schema(
            description = "Book publication date",
            example = "2008-08-11"
    )
    @NotNull(message = "Published date cannot be null")
    private LocalDate publishedDate;

    @Schema(
            description = "Author ID",
            example = "1"
    )
    @NotNull(message = "Author ID cannot be null")
    @Positive(message = "Author ID must be positive")
    private Long authorId;
}