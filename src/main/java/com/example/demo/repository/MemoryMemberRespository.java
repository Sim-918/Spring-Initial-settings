package com.example.demo.repository;

import com.example.demo.domain.Member;

import java.util.*;

//회원 정보를 메모리에 저장하고 관리하는 패키지

public class MemoryMemberRespository implements MemberRepository{

    // 회원 정보를 저장하는 HashMap. key는 회원 ID, value는 Member 객체
    private Map<Long, Member> store = new HashMap<>();
    // 회원 ID를 생성하기 위한 sequence 변수. static으로 선언되어 모든 인스턴스에서 공유
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        // sequence 값을 1 증가시켜 회원 ID로 설정
        member.setId(++sequence);
        // HashMap에 회원 ID와 Member 객체를 저장
        store.put(member.getId(), member);
        // 저장된 Member 객체 반환
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // HashMap에서 id에 해당하는 Member 객체를 찾아 Optional로 반환.
        // 만약 id에 해당하는 Member 객체가 없으면 Optional.empty() 반환
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // HashMap의 모든 value(Member 객체)를 stream으로 변환하여
        // 이름이 같은 Member 객체를 찾아 Optional로 반환.
        // 만약 이름에 해당하는 Member 객체가 없으면 Optional.empty() 반환
        return store.values().stream()
                .filter(m -> m.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        // HashMap의 모든 value(Member 객체)를 ArrayList로 변환하여 반환
        return new ArrayList<>(store.values());
    }
}