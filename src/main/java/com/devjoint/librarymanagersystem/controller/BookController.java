
package com.devjoint.librarymanagersystem.controller;

import com.devjoint.librarymanagersystem.model.dto.request.BookRequest;
import com.devjoint.librarymanagersystem.model.dto.response.BookResponse;
import com.devjoint.librarymanagersystem.service.BookService;
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

@RestController
@Tag(name = "Book Controller", description = "Operations related to books")
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Create a new book")
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        return new ResponseEntity<>(bookService.createBook(bookRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping
    @Operation(summary = "Get all books with pagination and sorting")
    public ResponseEntity<Page<BookResponse>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Search books by title with pagination and sorting")
    public ResponseEntity<Page<BookResponse>> searchBooks(@RequestParam String title, Pageable pageable) {
        return ResponseEntity.ok(bookService.searchBooks(title, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book by ID")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/price-range")
    public ResponseEntity<Page<BookResponse>> getBooksByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(bookService.getBooksByPriceRange(minPrice, maxPrice, pageable));
    }

    @GetMapping("/author")
    public ResponseEntity<Page<BookResponse>> getBooksByAuthor(
            @RequestParam String authorName,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorName, pageable));
    }

    @GetMapping("/category")
    public ResponseEntity<Page<BookResponse>> getBooksByCategory(
            @RequestParam String categoryName,
          @ParameterObject  Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooksByCategory(categoryName, pageable));
    }

    @GetMapping("/jpql")
    public ResponseEntity<Page<BookResponse>> getBooksWithPriceGreaterThan(
            @RequestParam Double price,
           @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooksWithPriceGreaterThan(price, pageable));
    }

    @GetMapping("/native")
    public ResponseEntity<Page<BookResponse>> getBooksWithPriceGreaterThanNative(
            @RequestParam Double price,
           @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooksWithPriceGreaterThanNative(price, pageable));
    }
}
