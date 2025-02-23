package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemoryMemberRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional // 테스트 실행 후 DB에 실제로 데이터가 저장되지 않고 롤백됨
public class MemberService {

    // MemberRepository 인터페이스를 구현한 MemoryMemberRepository 객체를 생성 (기존 코드)
    // private final MemberRepository memberRepository = new MemoryMemberRespository();
    // 위 방식은 고정된 저장소를 사용하게 되어 테스트 시 다른 저장소를 주입하기 어려움 (유연성이 부족함)

    // 의존성을 외부에서 주입받도록 변경 (DI: Dependency Injection)
    private MemberRepository memberRepository;


    // 생성자를 통해 MemberRepository를 주입받음 → 유연성이 증가함
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
     * 회원가입 메서드
     * - 새로운 회원을 등록하는 기능을 수행
     * - 중복된 회원이 존재하는지 검증한 후 저장
     * - 회원 가입이 완료되면 해당 회원의 ID를 반환
     */
    public Long join(Member member) {
        vaildateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member); // 회원 저장
        return member.getId(); // 회원 ID 반환
    }

    /*
     * 중복 회원 검증 메서드
     * - 회원의 이름을 이용해 기존에 같은 이름을 가진 회원이 존재하는지 확인
     * - 존재할 경우 IllegalStateException 예외를 발생시킴
     */
    private void vaildateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다."); // 중복 회원 예외 처리
                });
    }

    /*
     * 전체 회원 조회 메서드
     * - 저장된 모든 회원 목록을 반환
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /*
     * 특정 회원 조회 메서드
     * - 회원 ID를 기반으로 특정 회원을 검색하여 반환
     * - Optional로 감싸 반환하여, 회원이 존재하지 않을 경우 대비
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
