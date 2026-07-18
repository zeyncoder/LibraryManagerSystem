
package com.devjoint.librarymanagersystem.service;


import com.devjoint.librarymanagersystem.exception.ResourceNotFoundException;
import com.devjoint.librarymanagersystem.mapper.BookMapper;
import com.devjoint.librarymanagersystem.model.dto.request.BookRequest;
import com.devjoint.librarymanagersystem.model.dto.response.AuthorResponse;
import com.devjoint.librarymanagersystem.model.dto.response.BookResponse;
import com.devjoint.librarymanagersystem.model.entity.Author;
import com.devjoint.librarymanagersystem.model.entity.Book;
import com.devjoint.librarymanagersystem.repository.AuthorRepository;
import com.devjoint.librarymanagersystem.repository.BookRepository;
import com.devjoint.librarymanagersystem.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Author author;
    private Book book;
    private BookRequest bookRequest;
    private BookResponse bookResponse;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "John Doe", "john.doe@example.com", null);
        book = new Book(1L, "Spring Boot in Action", "978-1617297571", 45.0, LocalDate.of(2020, 1, 1), author);
        bookRequest = new BookRequest("Spring Boot in Action", "978-1617297571", 45.0, LocalDate.of(2020, 1, 1), 1L);
        bookResponse = new BookResponse(1L, "Spring Boot in Action", "978-1617297571", 45.0, LocalDate.of(2020, 1, 1), new AuthorResponse(1L, "John Doe", "john.doe@example.com"));
    }

    @Test
    void createBook() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookMapper.toEntity(bookRequest)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponse(book)).thenReturn(bookResponse);

        BookResponse result = bookService.createBook(bookRequest);

        assertNotNull(result);
        assertEquals(bookResponse, result);
        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void createBook_AuthorNotFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.createBook(bookRequest));
        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void getBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toResponse(book)).thenReturn(bookResponse);

        BookResponse result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(bookResponse, result);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_NotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getAllBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = Arrays.asList(book, new Book(2L, "Clean Code", "978-0132350884", 35.0, LocalDate.of(2008, 8, 1), author));
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        List<BookResponse> bookResponses = Arrays.asList(bookResponse, new BookResponse(2L, "Clean Code", "978-0132350884", 35.0, LocalDate.of(2008, 8, 1), new AuthorResponse(1L, "John Doe", "john.doe@example.com")));

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toResponse(books.get(0))).thenReturn(bookResponses.get(0));
        when(bookMapper.toResponse(books.get(1))).thenReturn(bookResponses.get(1));

        Page<BookResponse> result = bookService.getAllBooks(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(bookResponses, result.getContent());
        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    void searchBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = Arrays.asList(book);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        List<BookResponse> bookResponses = Arrays.asList(bookResponse);

        when(bookRepository.findByTitleContainingIgnoreCase("spring", pageable)).thenReturn(bookPage);
        when(bookMapper.toResponse(book)).thenReturn(bookResponse);

        Page<BookResponse> result = bookService.searchBooks("spring", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(bookResponses, result.getContent());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("spring", pageable);
    }

    @Test
    void updateBook() {
        Book updatedBook = new Book(1L, "Spring Boot in Action Updated", "978-1617297572", 50.0, LocalDate.of(2021, 1, 1), author);
        BookRequest updatedBookRequest = new BookRequest("Spring Boot in Action Updated", "978-1617297572", 50.0, LocalDate.of(2021, 1, 1), 1L);
        BookResponse updatedBookResponse = new BookResponse(1L, "Spring Boot in Action Updated", "978-1617297572", 50.0, LocalDate.of(2021, 1, 1), new AuthorResponse(1L, "John Doe", "john.doe@example.com"));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        doNothing().when(bookMapper).updateEntityFromRequest(updatedBookRequest, book);
        when(bookRepository.save(book)).thenReturn(updatedBook);
        when(bookMapper.toResponse(updatedBook)).thenReturn(updatedBookResponse);

        BookResponse result = bookService.updateBook(1L, updatedBookRequest);

        assertNotNull(result);
        assertEquals(updatedBookResponse, result);
        verify(bookRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).findById(1L);
        verify(bookMapper, times(1)).updateEntityFromRequest(updatedBookRequest, book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_NotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(1L, bookRequest));
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void updateBook_AuthorNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(1L, bookRequest));
        verify(bookRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void deleteBook_NotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).delete(any(Book.class));
    }
}
