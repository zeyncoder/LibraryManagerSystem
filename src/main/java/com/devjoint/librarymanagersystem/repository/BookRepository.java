package com.devjoint.librarymanagersystem.repository;

import com.devjoint.librarymanagersystem.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByAuthorFullNameContainingIgnoreCase(String fullName, Pageable pageable);
    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByCategoriesNameIgnoreCase(String categoryName, Pageable pageable);

    @Query("""
       SELECT b
       FROM Book b
       WHERE b.price > :price
       """)
    Page<Book> findBooksWithPriceGreaterThan(Double price, Pageable pageable);

    @Query(value = """
        SELECT *
        FROM books
        WHERE price > :price
        """, nativeQuery = true)
    Page<Book> findBooksWithPriceGreaterThanNative(Double price, Pageable pageable);
    @Override
    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findAll(Pageable pageable);
    @Override
    @EntityGraph(attributePaths = {"author", "categories"})
    Optional<Book> findById(Long id);
}