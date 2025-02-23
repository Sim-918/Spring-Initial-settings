package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

//JPA 사용하여 객체를 자동으로 테이블과 매핑하는 라이브러리
//현존하는 DB연동 방법 중 가장 쉽고 가장 좋은 라이브러리

public class JpaMemberRepository implements MemberRepository {


// EntityManager JPA가 주입
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name",name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        /*List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return result; --> 변수 인라인화*/
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
