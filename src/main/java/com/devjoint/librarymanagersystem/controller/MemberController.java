package com.devjoint.librarymanagersystem.controller;

import com.devjoint.librarymanagersystem.model.dto.request.MemberRequest;
import com.devjoint.librarymanagersystem.model.dto.response.MemberResponse;
import com.devjoint.librarymanagersystem.service.MemberService;
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
        name = "Member Controller",
        description = "Operations related to members"
)
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(
            summary = "Create a new member",
            description = "Creates a new member using the provided member information"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Member created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<MemberResponse> createMember(
            @Valid @RequestBody MemberRequest memberRequest) {

        return new ResponseEntity<>(
                memberService.createMember(memberRequest),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get member by ID",
            description = "Retrieves a member by its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public ResponseEntity<MemberResponse> getMemberById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                memberService.getMemberById(id)
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all members",
            description = "Retrieves all registered members"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Members retrieved successfully")
    })
    public ResponseEntity<List<MemberResponse>> getAllMembers() {

        return ResponseEntity.ok(
                memberService.getAllMembers()
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a member",
            description = "Updates an existing member by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequest memberRequest) {

        return ResponseEntity.ok(
                memberService.updateMember(id, memberRequest)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a member",
            description = "Deletes a member by its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long id) {

        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}