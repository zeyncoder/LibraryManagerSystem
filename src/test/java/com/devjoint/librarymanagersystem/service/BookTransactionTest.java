package com.devjoint.librarymanagersystem.service;

import com.devjoint.librarymanagersystem.model.dto.request.BookRequest;
import com.devjoint.librarymanagersystem.model.entity.Author;
import com.devjoint.librarymanagersystem.model.entity.Category;
import com.devjoint.librarymanagersystem.repository.AuthorRepository;
import com.devjoint.librarymanagersystem.repository.BookRepository;
import com.devjoint.librarymanagersystem.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class BookTransactionTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void createBookAndFail_shouldRollbackTransaction() {

        Author author = new Author();
        author.setFullName("Rollback Author");
        author.setEmail("rollback@test.com");
        author = authorRepository.save(author);

        Category category = new Category();
        category.setName("Rollback Category");
        category = categoryRepository.save(category);

        BookRequest request = new BookRequest();
        request.setTitle("Rollback Book");
        request.setIsbn("ROLLBACK-123");
        request.setPrice(50.0);
        request.setPublishedDate(LocalDate.now());
        request.setAuthorId(author.getId());
        request.setCategoryIds(Set.of(category.getId()));

        long before = bookRepository.count();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> bookService.createBookAndFail(request)
        );

        assertEquals("Rollback test", exception.getMessage());
        assertEquals(before, bookRepository.count());
    }
}
