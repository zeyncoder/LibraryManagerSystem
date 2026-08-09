package com.devjoint.librarymanagersystem.service;

import com.devjoint.librarymanagersystem.model.dto.request.BookRequest;
import com.devjoint.librarymanagersystem.model.dto.response.BookResponse;
import com.devjoint.librarymanagersystem.model.entity.Author;
import com.devjoint.librarymanagersystem.model.entity.Category;
import com.devjoint.librarymanagersystem.repository.AuthorRepository;
import com.devjoint.librarymanagersystem.repository.BookRepository;
import com.devjoint.librarymanagersystem.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;



@SpringBootTest
@ActiveProfiles("test")
class BookIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CacheManager cacheManager;

    @Test
    void createBook_shouldSaveSuccessfully() {

        Author author = new Author();
        author.setFullName("Integration Author");
        author.setEmail("integration@test.com");
        author = authorRepository.save(author);

        Category category = new Category();
        category.setName("Java");
        category = categoryRepository.save(category);

        BookRequest request = new BookRequest();
        request.setTitle("Spring Boot");
        request.setIsbn("ISBN-111");
        request.setPrice(35.0);
        request.setPublishedDate(LocalDate.now());
        request.setAuthorId(author.getId());
        request.setCategoryIds(Set.of(category.getId()));

        BookResponse response = bookService.createBook(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("Spring Boot", response.getTitle());
        assertEquals(1, response.getCategories().size());
    }

    @Test
    void getBookById_shouldReturnBook() {

        Author author = new Author();
        author.setFullName("John Doe");
        author.setEmail("john@test.com");
        author = authorRepository.save(author);

        Category category = new Category();
        category.setName("Backend");
        category = categoryRepository.save(category);

        BookRequest request = new BookRequest();
        request.setTitle("Clean Architecture");
        request.setIsbn("ISBN-222");
        request.setPrice(45.0);
        request.setPublishedDate(LocalDate.now());
        request.setAuthorId(author.getId());
        request.setCategoryIds(Set.of(category.getId()));

        BookResponse created = bookService.createBook(request);

        BookResponse found = bookService.getBookById(created.getId());

        assertNotNull(found);
        assertEquals(created.getId(), found.getId());
        assertEquals("Clean Architecture", found.getTitle());
    }

    @Test
    void deleteBook_shouldDeleteBook() {

        Author author = new Author();
        author.setFullName("Delete Author");
        author.setEmail("delete@test.com");
        author = authorRepository.save(author);

        Category category = new Category();
        category.setName("Delete Category");
        category = categoryRepository.save(category);

        BookRequest request = new BookRequest();
        request.setTitle("Delete Book");
        request.setIsbn("ISBN-333");
        request.setPrice(25.0);
        request.setPublishedDate(LocalDate.now());
        request.setAuthorId(author.getId());
        request.setCategoryIds(Set.of(category.getId()));

        BookResponse created = bookService.createBook(request);

        assertTrue(bookRepository.findById(created.getId()).isPresent());

        bookService.deleteBook(created.getId());

        assertFalse(bookRepository.findById(created.getId()).isPresent());
    }
    @Test
    void getBookById_shouldUseCache() {

        Author author = new Author();
        author.setFullName("Cache Author");
        author.setEmail("cache@test.com");
        author = authorRepository.save(author);

        Category category = new Category();
        category.setName("Cache Category");
        category = categoryRepository.save(category);

        BookRequest request = new BookRequest();
        request.setTitle("Cached Book");
        request.setIsbn("CACHE-123");
        request.setPrice(30.0);
        request.setPublishedDate(LocalDate.now());
        request.setAuthorId(author.getId());
        request.setCategoryIds(Set.of(category.getId()));

        BookResponse created = bookService.createBook(request);

        Cache cache = cacheManager.getCache("books");

        assertNotNull(cache);

        cache.clear();

        BookResponse firstResult = bookService.getBookById(created.getId());

        assertNotNull(firstResult);
        assertEquals(created.getId(), firstResult.getId());

        assertNotNull(cache.get(created.getId()));

        BookResponse secondResult = bookService.getBookById(created.getId());

        assertNotNull(secondResult);
        assertEquals(firstResult.getId(), secondResult.getId());
        assertEquals(firstResult.getTitle(), secondResult.getTitle());
    }
    @Test
    void updateBook_shouldEvictCache() {

        Author author = new Author();
        author.setFullName("Cache Test Author");
        author.setEmail("cache-update@test.com");
        author = authorRepository.save(author);

        Category category = new Category();
        category.setName("Cache Test");
        category = categoryRepository.save(category);

        BookRequest request = new BookRequest();
        request.setTitle("Old Title");
        request.setIsbn("CACHE-UPDATE-123");
        request.setPrice(30.0);
        request.setPublishedDate(LocalDate.now());
        request.setAuthorId(author.getId());
        request.setCategoryIds(Set.of(category.getId()));

        BookResponse created = bookService.createBook(request);


        BookResponse first = bookService.getBookById(created.getId());

        assertEquals("Old Title", first.getTitle());


        request.setTitle("Updated Title");

        bookService.updateBook(created.getId(), request);


        BookResponse updated = bookService.getBookById(created.getId());

        assertEquals("Updated Title", updated.getTitle());
    }
}