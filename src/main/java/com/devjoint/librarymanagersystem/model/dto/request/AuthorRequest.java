package com.devjoint.librarymanagersystem.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequest {
    @NotBlank(message = "Author full name cannot be empty")
    private String fullName;

    @NotBlank(message = "Author email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
}
