package com.devjoint.librarymanagersystem.mapper;

import com.devjoint.librarymanagersystem.model.dto.request.MemberRequest;
import com.devjoint.librarymanagersystem.model.dto.response.MemberResponse;
import com.devjoint.librarymanagersystem.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "id", ignore = true)
    Member toEntity(MemberRequest memberRequest);

    MemberResponse toResponse(Member member);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(MemberRequest memberRequest, @MappingTarget Member member);
}