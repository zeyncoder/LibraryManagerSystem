package com.devjoint.librarymanagersystem.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating or updating a member")
public class MemberRequest {

    @Schema(
            description = "Member full name",
            example = "John Doe"
    )
    @NotBlank(message = "Member full name cannot be empty")
    @Size(min = 2, max = 50)
    private String fullName;

    @Schema(
            description = "Member email address",
            example = "john.doe@example.com"
    )
    @NotBlank(message = "Member email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
            description = "Member phone number",
            example = "+994501234567"
    )
    @NotBlank(message = "Member phone number cannot be empty")
    private String phone;
}