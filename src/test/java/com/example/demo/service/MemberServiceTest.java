package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemoryMemberRespository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // 테스트할 대상 클래스 (회원 서비스)
    MemberService memberService;
    // 테스트에서 사용할 가짜 저장소 (메모리 기반 저장소)
    MemoryMemberRespository memberRespository;

    @BeforeEach // 각 테스트 실행 전에 실행되는 메서드
    public void beforeEach() {
        // MemoryMemberRepository의 새로운 인스턴스를 생성 (매 테스트마다 초기화)
        memberRespository = new MemoryMemberRespository();
        // MemberService에 새롭게 만든 MemoryMemberRepository를 주입하여 테스트 준비
        memberService = new MemberService(memberRespository);
    }

    @AfterEach // 각 테스트 실행 후 실행되는 메서드
    public void afterEach() {
        // 테스트가 끝난 후 저장소를 비워서 다음 테스트에 영향을 주지 않도록 함
        memberRespository.clearStore();
    }
//한글로 바꿔도됨!
    @Test
    void 회원가입() {
        //given
        Member member=new Member();
        member.setName("hello");

        //when
        Long saveId=memberService.join(member);

        //then
        Member findMember=memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1=new Member();
        member1.setName("spring");

        Member member2=new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        IllegalStateException e= assertThrows(IllegalStateException.class, ()-> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        try {
//            memberService.join(member2);
//            fail("예외가 발생해야 합니다.");
//        } catch (IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.23123123");
//        }
        //that
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}