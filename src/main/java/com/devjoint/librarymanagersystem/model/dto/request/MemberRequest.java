package com.devjoint.librarymanagersystem.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
    @NotBlank(message = "Member full name cannot be empty")
    @Size(min = 2, max = 50)
    private String fullName;

    @NotBlank(message = "Member email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Member phone number cannot be empty")
    private String phone;
}
