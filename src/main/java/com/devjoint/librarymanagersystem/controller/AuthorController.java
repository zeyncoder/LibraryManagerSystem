
package com.devjoint.librarymanagersystem.controller;


import com.devjoint.librarymanagersystem.model.dto.request.AuthorRequest;
import com.devjoint.librarymanagersystem.model.dto.response.AuthorResponse;
import com.devjoint.librarymanagersystem.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Tag(
        name = "Author Controller",
        description = "Operations related to authors"
)
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    @Operation(
            summary = "Create a new author",
            description = "Creates a new author using the provided author information"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<AuthorResponse> createAuthor(
            @Valid @RequestBody AuthorRequest authorRequest) {

        return new ResponseEntity<>(
                authorService.createAuthor(authorRequest),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get author by ID",
            description = "Retrieves an author by its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public ResponseEntity<AuthorResponse> getAuthorById(
            @PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get all authors",
            description = "Retrieves all authors"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authors retrieved successfully")
    })
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an author",
            description = "Updates an existing author by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequest authorRequest) {

        return ResponseEntity.ok(
                authorService.updateAuthor(id, authorRequest)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an author",
            description = "Deletes an author by its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}