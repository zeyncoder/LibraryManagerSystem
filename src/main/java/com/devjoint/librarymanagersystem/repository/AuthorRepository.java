package com.devjoint.librarymanagersystem.repository;

import com.devjoint.librarymanagersystem.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
