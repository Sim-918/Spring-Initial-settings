package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository를 상속하면 기본 CRUD 메서드가 자동 생성됨  --> 스프링데이터 jpa
public interface SpringDataJpaMemberRepository  extends JpaRepository<Member,Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
