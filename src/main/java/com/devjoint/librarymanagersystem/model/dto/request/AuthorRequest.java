package com.devjoint.librarymanagersystem.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating or updating an author")
public class AuthorRequest {

    @Schema(
            description = "Author full name",
            example = "Robert C. Martin"
    )
    @NotBlank(message = "Author full name cannot be empty")
    private String fullName;

    @Schema(
            description = "Author email",
            example = "unclebob@example.com"
    )
    @NotBlank(message = "Author email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
}