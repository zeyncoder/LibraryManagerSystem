package com.devjoint.librarymanagersystem.repository;

import com.devjoint.librarymanagersystem.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Book> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    Page<Book> findByAuthorFullNameContainingIgnoreCase(String fullName, Pageable pageable);

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
}