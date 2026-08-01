package com.devjoint.librarymanagersystem.specification;

import com.devjoint.librarymanagersystem.model.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) ->
                title == null || title.isBlank()
                        ? null
                        : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) ->
                author == null || author.isBlank()
                        ? null
                        : cb.like(
                        cb.lower(root.get("author").get("fullName")),
                        "%" + author.toLowerCase() + "%"
                );
    }

    public static Specification<Book> hasCategory(String category) {
        return (root, query, cb) ->
                category == null || category.isBlank()
                        ? null
                        : cb.equal(
                        cb.lower(root.join("categories").get("name")),
                        category.toLowerCase()
                );
    }

    public static Specification<Book> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            if (minPrice == null) {
                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
            if (maxPrice == null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
            return cb.between(root.get("price"), minPrice, maxPrice);
        };
    }
}