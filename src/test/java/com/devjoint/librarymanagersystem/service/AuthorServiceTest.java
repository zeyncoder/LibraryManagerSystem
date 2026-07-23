
package com.devjoint.librarymanagersystem.service;


import com.devjoint.librarymanagersystem.exception.ResourceNotFoundException;
import com.devjoint.librarymanagersystem.mapper.AuthorMapper;
import com.devjoint.librarymanagersystem.model.dto.request.AuthorRequest;
import com.devjoint.librarymanagersystem.model.dto.response.AuthorResponse;
import com.devjoint.librarymanagersystem.model.entity.Author;
import com.devjoint.librarymanagersystem.repository.AuthorRepository;
import com.devjoint.librarymanagersystem.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;
    private AuthorRequest authorRequest;
    private AuthorResponse authorResponse;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "John Doe", "john.doe@example.com", null);
        authorRequest = new AuthorRequest("John Doe", "john.doe@example.com");
        authorResponse = new AuthorResponse(1L, "John Doe", "john.doe@example.com");
    }

    @Test
    void createAuthor() {
        when(authorMapper.toEntity(authorRequest)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toResponse(author)).thenReturn(authorResponse);

        AuthorResponse result = authorService.createAuthor(authorRequest);

        assertNotNull(result);
        assertEquals(authorResponse, result);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void getAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toResponse(author)).thenReturn(authorResponse);

        AuthorResponse result = authorService.getAuthorById(1L);

        assertNotNull(result);
        assertEquals(authorResponse, result);
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void getAuthorById_NotFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorById(1L));
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void getAllAuthors() {
        List<Author> authors = Arrays.asList(author, new Author(2L, "Jane Smith", "jane.smith@example.com", null));
        List<AuthorResponse> authorResponses = Arrays.asList(authorResponse, new AuthorResponse(2L, "Jane Smith", "jane.smith@example.com"));

        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.toResponse(authors.get(0))).thenReturn(authorResponses.get(0));
        when(authorMapper.toResponse(authors.get(1))).thenReturn(authorResponses.get(1));

        List<AuthorResponse> result = authorService.getAllAuthors();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(authorResponses, result);
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void updateAuthor() {
        Author updatedAuthor = new Author(1L, "John Doe Updated", "john.doe.updated@example.com", null);
        AuthorRequest updatedAuthorRequest = new AuthorRequest("John Doe Updated", "john.doe.updated@example.com");
        AuthorResponse updatedAuthorResponse = new AuthorResponse(1L, "John Doe Updated", "john.doe.updated@example.com");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        lenient().doAnswer(invocation -> {
            Author a = invocation.getArgument(1);
            a.setFullName(updatedAuthorRequest.getFullName());
            a.setEmail(updatedAuthorRequest.getEmail());
            return null;
        }).when(authorMapper).updateEntityFromRequest(updatedAuthorRequest, author);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toResponse(author)).thenReturn(updatedAuthorResponse);

        AuthorResponse result = authorService.updateAuthor(1L, updatedAuthorRequest);

        assertNotNull(result);
        assertEquals(updatedAuthorResponse, result);
        verify(authorRepository, times(1)).findById(1L);
        verify(authorMapper, times(1)).updateEntityFromRequest(updatedAuthorRequest, author);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void updateAuthor_NotFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.updateAuthor(1L, authorRequest));
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void deleteAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        doNothing().when(authorRepository).delete(author);

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    void deleteAuthor_NotFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.deleteAuthor(1L));
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, never()).delete(any(Author.class));
    }
}
