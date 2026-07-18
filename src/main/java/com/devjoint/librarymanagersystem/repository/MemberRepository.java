package com.devjoint.librarymanagersystem.repository;

import com.devjoint.librarymanagersystem.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
