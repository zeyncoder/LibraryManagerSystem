package com.devjoint.librarymanagersystem.service;

import com.devjoint.librarymanagersystem.model.dto.request.AuthorRequest;
import com.devjoint.librarymanagersystem.model.dto.response.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse createAuthor(AuthorRequest authorRequest);
    AuthorResponse getAuthorById(Long id);
    List<AuthorResponse> getAllAuthors();
    AuthorResponse updateAuthor(Long id, AuthorRequest authorRequest);
    void deleteAuthor(Long id);
}