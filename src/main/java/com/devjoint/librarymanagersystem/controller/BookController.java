
package com.devjoint.librarymanagersystem.controller;

import com.devjoint.librarymanagersystem.model.dto.request.BookRequest;
import com.devjoint.librarymanagersystem.model.dto.response.BookResponse;
import com.devjoint.librarymanagersystem.model.dto.response.FileDownloadResponse;
import com.devjoint.librarymanagersystem.service.BookService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Book Controller", description = "Operations related to books")
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(
            summary = "Create a new book",
            description = "Creates a new book and stores it in the database."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Book created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid book data"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            )
    })
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        return new ResponseEntity<>(bookService.createBook(bookRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get book by ID",
            description = "Retrieves a book using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get all books",
            description = "Retrieves all books with pagination and sorting support"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully")
    })
    public ResponseEntity<Page<BookResponse>> getAllBooks(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Search books by title with pagination and sorting")
    public ResponseEntity<Page<BookResponse>> searchBooks(@RequestParam String title, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookService.searchBooks(title, pageable));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a book",
            description = "Updates an existing book by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a book",
            description = "Deletes a book by its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/price-range")
    @Operation(summary = "Get books by price range")
    public ResponseEntity<Page<BookResponse>> getBooksByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(bookService.getBooksByPriceRange(minPrice, maxPrice, pageable));
    }

    @GetMapping("/author")
    @Operation(summary = "Get books by author")
    public ResponseEntity<Page<BookResponse>> getBooksByAuthor(
            @RequestParam String authorName,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorName, pageable));
    }

    @GetMapping("/category")
    @Operation(summary = "Get books by category")
    public ResponseEntity<Page<BookResponse>> getBooksByCategory(
            @RequestParam String categoryName,
          @ParameterObject  Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooksByCategory(categoryName, pageable));
    }

    @GetMapping("/jpql")
    @Operation(summary = "Get books using JPQL query")
    public ResponseEntity<Page<BookResponse>> getBooksWithPriceGreaterThan(
            @RequestParam Double price,
           @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooksWithPriceGreaterThan(price, pageable));
    }

    @GetMapping("/native")
    @Operation(summary = "Get books using native SQL query")
    public ResponseEntity<Page<BookResponse>> getBooksWithPriceGreaterThanNative(
            @RequestParam Double price,
           @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooksWithPriceGreaterThanNative(price, pageable));
    }
    @GetMapping("/filter")
    @Operation(summary = "Dynamic book filtering")
    public ResponseEntity<Page<BookResponse>> filterBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(
                bookService.filterBooks(
                        title,
                        author,
                        category,
                        minPrice,
                        maxPrice,
                        pageable
                )
        );
    }
    @PostMapping(
            value = "/{id}/cover",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            summary = "Upload book cover",
            description = "Uploads a cover image for the specified book."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book cover uploaded successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid file type or file size"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found"
            )
    })
    public ResponseEntity<String> uploadCover(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                bookService.uploadCover(id, file)
        );
    }
    @GetMapping("/{id}/cover")
    @Operation(
            summary = "Download book cover",
            description = "Downloads the cover image associated with the specified book."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book cover downloaded successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book cover not found"
            )
    })
    public ResponseEntity<ByteArrayResource> downloadCover(
            @PathVariable Long id) {
        FileDownloadResponse file = bookService.downloadCover(id);
        ByteArrayResource resource =
                new ByteArrayResource(file.data());
        return ResponseEntity.ok()
                .contentType(file.contentType())
                .body(resource);
    }

}
