package com.example.demo.repository;

import com.example.demo.domain.Member;
import java.util.Optional;
import java.util.List;

//Optional->null값을 안전하게 객체로 감싸고 변환
public interface MemberRepository {
//    회원 저장 메서드: Member 객체를 받아 저장하고, 저장된 Member 객체를 반환
    Member save(Member member);
//    ID로 회원 검색 메서드: Long타입
    Optional<Member> findById(Long id);
//    이름으로 회원 검색 메서드: String타입
    Optional<Member> findByName(String name);
//    모든 회원 조회 메서드: 저장된 모든 회원을 List형태로 반환
    List<Member> findAll();
}
