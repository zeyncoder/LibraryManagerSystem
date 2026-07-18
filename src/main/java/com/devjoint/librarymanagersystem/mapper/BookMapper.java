package com.devjoint.librarymanagersystem.mapper;


import com.devjoint.librarymanagersystem.model.dto.request.BookRequest;
import com.devjoint.librarymanagersystem.model.dto.response.BookResponse;
import com.devjoint.librarymanagersystem.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "author.id", source = "authorId")
    Book toEntity(BookRequest bookRequest);

    @Mapping(target = "author", source = "author")
    BookResponse toResponse(Book book);

    @Mapping(target = "author.id", source = "authorId")
    void updateEntityFromRequest(BookRequest bookRequest, @MappingTarget Book book);
}
