package com.devjoint.librarymanagersystem.service.impl;

import com.devjoint.librarymanagersystem.exception.ResourceNotFoundException;
import com.devjoint.librarymanagersystem.mapper.BookMapper;
import com.devjoint.librarymanagersystem.model.dto.request.BookRequest;
import com.devjoint.librarymanagersystem.model.dto.response.BookResponse;
import com.devjoint.librarymanagersystem.model.entity.Author;
import com.devjoint.librarymanagersystem.model.entity.Book;
import com.devjoint.librarymanagersystem.model.entity.Category;
import com.devjoint.librarymanagersystem.repository.AuthorRepository;
import com.devjoint.librarymanagersystem.repository.BookRepository;
import com.devjoint.librarymanagersystem.repository.CategoryRepository;
import com.devjoint.librarymanagersystem.service.BookService;
import com.devjoint.librarymanagersystem.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    @Transactional
    @Override
    public BookResponse createBook(BookRequest bookRequest) {

        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + bookRequest.getAuthorId()));

        Book book = bookMapper.toEntity(bookRequest);
        book.setAuthor(author);

        if (bookRequest.getCategoryIds() != null && !bookRequest.getCategoryIds().isEmpty()) {

            Set<Category> categories = new HashSet<>(
                    categoryRepository.findAllByIdIn(bookRequest.getCategoryIds())
            );

            book.setCategories(categories);
        }

        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponse(savedBook);
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + id));
        return bookMapper.toResponse(book);
    }

    @Override
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toResponse);
    }

    @Override
    public Page<BookResponse> searchBooks(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map(bookMapper::toResponse);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + id));

        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + bookRequest.getAuthorId()));

        bookMapper.updateEntityFromRequest(bookRequest, existingBook);

        existingBook.setAuthor(author);

        if (bookRequest.getCategoryIds() != null && !bookRequest.getCategoryIds().isEmpty()) {

            Set<Category> categories = new HashSet<>(
                    categoryRepository.findAllByIdIn(bookRequest.getCategoryIds())
            );

            existingBook.setCategories(categories);
        } else {
            existingBook.getCategories().clear();
        }

        return bookMapper.toResponse(bookRepository.save(existingBook));
    }

    @Override
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + id));

        bookRepository.delete(book);
    }
    @Override
    public Page<BookResponse> getBooksByPriceRange(Double minPrice, Double maxPrice, Pageable pageable) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice, pageable)
                .map(bookMapper::toResponse);
    }

    @Override
    public Page<BookResponse> getBooksByAuthor(String authorName, Pageable pageable) {
        return bookRepository.findByAuthorFullNameContainingIgnoreCase(authorName, pageable)
                .map(bookMapper::toResponse);
    }

    @Override
    public Page<BookResponse> getBooksByCategory(String categoryName, Pageable pageable) {
        return bookRepository.findByCategoriesNameIgnoreCase(categoryName, pageable)
                .map(bookMapper::toResponse);
    }

    @Override
    public Page<BookResponse> getBooksWithPriceGreaterThan(Double price, Pageable pageable) {
        return bookRepository.findBooksWithPriceGreaterThan(price, pageable)
                .map(bookMapper::toResponse);
    }

    @Override
    public Page<BookResponse> getBooksWithPriceGreaterThanNative(Double price, Pageable pageable) {
        return bookRepository.findBooksWithPriceGreaterThanNative(price, pageable)
                .map(bookMapper::toResponse);
    }
    @Override
    public Page<BookResponse> filterBooks(
            String title,
            String author,
            String category,
            Double minPrice,
            Double maxPrice,
            Pageable pageable) {

        Specification<Book> specification = Specification
                .where(BookSpecification.hasTitle(title))
                .and(BookSpecification.hasAuthor(author))
                .and(BookSpecification.hasCategory(category))
                .and(BookSpecification.hasPriceBetween(minPrice, maxPrice));

        return bookRepository.findAll(specification, pageable)
                .map(bookMapper::toResponse);
    }
    @Transactional
    @Override
    public void createBookAndFail(BookRequest bookRequest) {

        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + bookRequest.getAuthorId()));

        Book book = bookMapper.toEntity(bookRequest);
        book.setAuthor(author);

        if (bookRequest.getCategoryIds() != null && !bookRequest.getCategoryIds().isEmpty()) {
            Set<Category> categories = new HashSet<>(
                    categoryRepository.findAllByIdIn(bookRequest.getCategoryIds())
            );
            book.setCategories(categories);
        }

        bookRepository.save(book);

        throw new RuntimeException("Rollback test");
    }
}