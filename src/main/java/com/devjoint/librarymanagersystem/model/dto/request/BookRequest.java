package com.devjoint.librarymanagersystem.model.dto.request;

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
public class BookRequest {
    @NotBlank(message = "Book title cannot be empty")
    private String title;

    @NotBlank(message = "Book ISBN cannot be empty")
    private String isbn;

    @NotNull(message = "Book price cannot be null")
    @Positive(message = "Book price must be positive")
    private Double price;

    @NotNull(message = "Published date cannot be null")
    private LocalDate publishedDate;

    @NotNull(message = "Author ID cannot be null")
    @Positive(message = "Author ID must be positive")
    private Long authorId;
}
