package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemoryMemberRespository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * MemberServiceIntegrationTest 클래스
 * - 회원 서비스(MemberService) 전체 기능을 검증하는 통합 테스트(Integration Test)
 * - 실제 DB와 연결하여 테스트 진행
 * - 데이터 정합성을 유지하기 위해 @Transactional을 사용하여 테스트 후 롤백 처리
 */

@SpringBootTest // 스프링 컨텍스트를 로드하여 테스트 (스프링 빈 자동 주입)
@Transactional // 테스트 실행 후 DB에 실제로 데이터가 저장되지 않고 롤백됨
class MemberServiceIntegrationTest {

    // 테스트할 대상 클래스 (회원 서비스)
    @Autowired
    MemberService memberService;

    // 테스트에서 사용할 실제 저장소 (JDBC 기반 저장소 또는 JPA 기반 저장소 사용 가능)
    @Autowired
    MemberRepository memberRepository;

    /**
     * 회원가입 테스트
     */
    @Test
//    @Commit
    void 회원가입() {
        // Given: 테스트 데이터 준비
        Member member = new Member();
        member.setName("spring100");

        // When: 회원 가입 실행
        Long saveId = memberService.join(member);

        // Then: 저장된 회원이 정상적으로 조회되는지 검증
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    /**
     * 중복 회원 가입 예외 테스트
     */
    @Test
    public void 중복_회원_예외() {
        // Given: 같은 이름을 가진 회원 두 명 생성
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // When: 첫 번째 회원 가입 실행
        memberService.join(member1);

        // Then: 두 번째 회원 가입 시 예외 발생 여부 검증
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}