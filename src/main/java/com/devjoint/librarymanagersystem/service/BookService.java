package com.devjoint.librarymanagersystem.service;

import com.devjoint.librarymanagersystem.model.dto.request.BookRequest;
import com.devjoint.librarymanagersystem.model.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookResponse createBook(BookRequest bookRequest);
    BookResponse getBookById(Long id);
    Page<BookResponse> getAllBooks(Pageable pageable);
    Page<BookResponse> searchBooks(String title, Pageable pageable);
    BookResponse updateBook(Long id, BookRequest bookRequest);
    void deleteBook(Long id);
    Page<BookResponse> getBooksByPriceRange(Double minPrice, Double maxPrice, Pageable pageable);

    Page<BookResponse> getBooksByAuthor(String authorName, Pageable pageable);

    Page<BookResponse> getBooksByCategory(String categoryName, Pageable pageable);

    Page<BookResponse> getBooksWithPriceGreaterThan(Double price, Pageable pageable);

    Page<BookResponse> getBooksWithPriceGreaterThanNative(Double price, Pageable pageable);
}