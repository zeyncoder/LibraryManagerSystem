package com.devjoint.librarymanagersystem.mapper;

import com.devjoint.librarymanagersystem.model.dto.request.MemberRequest;
import com.devjoint.librarymanagersystem.model.dto.response.MemberResponse;
import com.devjoint.librarymanagersystem.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member toEntity(MemberRequest memberRequest);
    MemberResponse toResponse(Member member);
    void updateEntityFromRequest(MemberRequest memberRequest, @MappingTarget Member member);
}
