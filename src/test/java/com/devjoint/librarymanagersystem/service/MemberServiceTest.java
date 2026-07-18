
package com.devjoint.librarymanagersystem.service;


import com.devjoint.librarymanagersystem.exception.ResourceNotFoundException;
import com.devjoint.librarymanagersystem.mapper.MemberMapper;
import com.devjoint.librarymanagersystem.model.dto.request.MemberRequest;
import com.devjoint.librarymanagersystem.model.dto.response.MemberResponse;
import com.devjoint.librarymanagersystem.model.entity.Member;
import com.devjoint.librarymanagersystem.repository.MemberRepository;
import com.devjoint.librarymanagersystem.service.impl.MemberServiceImpl;
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
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member;
    private MemberRequest memberRequest;
    private MemberResponse memberResponse;

    @BeforeEach
    void setUp() {
        member = new Member(1L, "Alice Smith", "alice.smith@example.com", "123-456-7890");
        memberRequest = new MemberRequest("Alice Smith", "alice.smith@example.com", "123-456-7890");
        memberResponse = new MemberResponse(1L, "Alice Smith", "alice.smith@example.com", "123-456-7890");
    }

    @Test
    void createMember() {
        when(memberMapper.toEntity(memberRequest)).thenReturn(member);
        when(memberRepository.save(member)).thenReturn(member);
        when(memberMapper.toResponse(member)).thenReturn(memberResponse);

        MemberResponse result = memberService.createMember(memberRequest);

        assertNotNull(result);
        assertEquals(memberResponse, result);
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void getMemberById() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberMapper.toResponse(member)).thenReturn(memberResponse);

        MemberResponse result = memberService.getMemberById(1L);

        assertNotNull(result);
        assertEquals(memberResponse, result);
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void getMemberById_NotFound() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> memberService.getMemberById(1L));
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void getAllMembers() {
        List<Member> members = Arrays.asList(member, new Member(2L, "Bob Johnson", "bob.johnson@example.com", "098-765-4321"));
        List<MemberResponse> memberResponses = Arrays.asList(memberResponse, new MemberResponse(2L, "Bob Johnson", "bob.johnson@example.com", "098-765-4321"));

        when(memberRepository.findAll()).thenReturn(members);
        when(memberMapper.toResponse(members.get(0))).thenReturn(memberResponses.get(0));
        when(memberMapper.toResponse(members.get(1))).thenReturn(memberResponses.get(1));

        List<MemberResponse> result = memberService.getAllMembers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(memberResponses, result);
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void updateMember() {
        Member updatedMember = new Member(1L, "Alice Smith Updated", "alice.smith.updated@example.com", "111-222-3333");
        MemberRequest updatedMemberRequest = new MemberRequest("Alice Smith Updated", "alice.smith.updated@example.com", "111-222-3333");
        MemberResponse updatedMemberResponse = new MemberResponse(1L, "Alice Smith Updated", "alice.smith.updated@example.com", "111-222-3333");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        doNothing().when(memberMapper).updateEntityFromRequest(updatedMemberRequest, member);
        when(memberRepository.save(member)).thenReturn(updatedMember);
        when(memberMapper.toResponse(updatedMember)).thenReturn(updatedMemberResponse);

        MemberResponse result = memberService.updateMember(1L, updatedMemberRequest);

        assertNotNull(result);
        assertEquals(updatedMemberResponse, result);
        verify(memberRepository, times(1)).findById(1L);
        verify(memberMapper, times(1)).updateEntityFromRequest(updatedMemberRequest, member);
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void updateMember_NotFound() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> memberService.updateMember(1L, memberRequest));
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void deleteMember() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        doNothing().when(memberRepository).delete(member);

        memberService.deleteMember(1L);

        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).delete(member);
    }

    @Test
    void deleteMember_NotFound() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> memberService.deleteMember(1L));
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, never()).delete(any(Member.class));
    }
}
