
package com.devjoint.librarymanagersystem.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String isbn;
    private Double price;
    private LocalDate publishedDate;
    private AuthorResponse author;
    private Set<CategoryResponse> categories;
}
