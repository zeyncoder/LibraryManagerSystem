package com.devjoint.librarymanagersystem.service;

import com.devjoint.librarymanagersystem.model.dto.request.MemberRequest;
import com.devjoint.librarymanagersystem.model.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {
    MemberResponse createMember(MemberRequest memberRequest);



    MemberResponse getMemberById(Long id);

    List<MemberResponse> getAllMembers();

    MemberResponse updateMember(Long id, MemberRequest memberRequest);

    void deleteMember(Long id);
}