package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * MemoryMemberRepositoryTest 클래스
 * - MemoryMemberRepository의 기능을 테스트하는 JUnit 단위 테스트 클래스입니다.
 * - 단위 테스트(Unit Test)로, 데이터베이스 없이 메모리 저장소에서 동작 확인 가능
 * - 회원 저장, 조회, 전체 조회 기능을 검증합니다.
 */

class MemoryMemberRespositoryTest {

    // 테스트 대상 MemoryMemberRepository 인스턴스 생성
    MemoryMemberRespository respository = new MemoryMemberRespository();

    // 각 테스트 실행 후 저장소 초기화
    @AfterEach
    public void afterEach() {
        respository.clearStore();
    }

    /**
     * save() 테스트
     * - 새로운 Member 객체를 저장한 후, 저장된 객체를 조회하여 원본과 일치하는지 확인
     */
    @Test
    public void save() {
        // 1. 새로운 회원 생성 및 이름 설정
        Member member = new Member();
        member.setName("spring");

        // 2. 회원 저장
        respository.save(member);

        // 3. 저장된 회원을 ID로 검색
        Member result = respository.findById(member.getId()).get();

        // 4. 원본 객체와 저장된 객체가 동일한지 검증
        assertThat(member).isEqualTo(result);
    }

    /**
     * findByName() 테스트
     * - 특정 이름을 가진 회원을 저장 후, 해당 이름으로 검색하여 기대한 객체가 반환되는지 확인
     */
    @Test
    public void findByName() {
        // 1. 회원 1 추가
        Member member1 = new Member();
        member1.setName("spring1");
        respository.save(member1);

        // 2. 회원 2 추가
        Member member2 = new Member();
        member2.setName("spring2");
        respository.save(member2);

        // 3. "spring1" 이름으로 검색
        Member result = respository.findByName("spring1").get();

        // 4. 검색된 회원이 member1과 동일한지 검증
        assertThat(result).isEqualTo(member1);
    }

    /**
     * findAll() 테스트
     * - 여러 명의 회원을 저장한 후, 전체 조회 시 저장된 회원 수가 올바른지 검증
     */
    @Test
    public void findAll() {
        // 1. 첫 번째 회원 추가
        Member member1 = new Member();
        member1.setName("spring1");
        respository.save(member1);

        // 2. 두 번째 회원 추가
        Member member2 = new Member();
        member2.setName("spring2");
        respository.save(member2);

        // 3. 저장된 모든 회원 목록 가져오기
        List<Member> result = respository.findAll();

        // 4. 저장된 회원 수가 정확한지 검증
        assertThat(result.size()).isEqualTo(2);
    }
}
