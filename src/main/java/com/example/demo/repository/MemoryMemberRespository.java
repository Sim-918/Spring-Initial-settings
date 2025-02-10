package com.example.demo.repository;

import com.example.demo.domain.Member;

import java.util.*;

// 회원 정보를 메모리에 저장하고 관리하는 클래스
// MemberRepository 인터페이스를 구현하여 회원 관련 데이터 관리 기능을 제공함
public class MemoryMemberRespository implements MemberRepository {

    // 회원 정보를 저장하는 HashMap
    // key: 회원 ID (Long)
    // value: 회원 객체 (Member)
    private static Map<Long, Member> store = new HashMap<>();

    // 회원 ID를 생성하기 위한 자동 증가 변수 (sequence)
    // static으로 선언하여 모든 인스턴스에서 공유됨
    private static long sequence = 0L;

    /**
     * 회원을 저장하는 메서드
     * 1. 새로운 회원 ID를 자동 생성하여 할당
     * 2. 회원 정보를 store(HashMap)에 저장
     * 3. 저장된 회원 객체 반환
     *
     * @param member 저장할 회원 객체
     * @return 저장된 회원 객체
     */
    @Override
    public Member save(Member member) {
        // sequence 값을 1 증가시켜 새로운 회원 ID로 설정
        member.setId(++sequence);

        // HashMap에 회원 ID를 key로, Member 객체를 value로 저장
        store.put(member.getId(), member);

        // 저장된 회원 객체 반환
        return member;
    }

    /**
     * ID를 기반으로 회원을 조회하는 메서드
     * - 해당 ID의 회원이 존재하면 Optional<Member>를 반환
     * - 존재하지 않으면 Optional.empty() 반환
     *
     * @param id 찾을 회원의 ID
     * @return 해당 ID의 회원 객체 (Optional로 감싸서 반환)
     */
    @Override
    public Optional<Member> findById(Long id) {
        // HashMap에서 id에 해당하는 회원 정보를 찾아 Optional로 감싸 반환
        // 존재하지 않으면 Optional.empty() 반환
        return Optional.ofNullable(store.get(id));
    }

    /**
     * 이름을 기반으로 회원을 조회하는 메서드
     * - store에 저장된 모든 회원을 대상으로 이름이 일치하는 회원을 검색
     * - 해당 회원이 존재하면 Optional<Member> 반환
     * - 존재하지 않으면 Optional.empty() 반환
     *
     * @param name 찾을 회원의 이름
     * @return 해당 이름을 가진 회원 객체 (Optional로 감싸서 반환)
     */
    @Override
    public Optional<Member> findByName(String name) {
        // store의 모든 value(Member 객체)를 stream으로 변환
        // filter()를 이용하여 입력된 name과 일치하는 Member 찾기
        // findAny()를 사용하여 첫 번째로 발견된 회원을 Optional로 반환
        return store.values().stream()
                .filter(m -> m.getName().equals(name))
                .findAny();
    }

    /**
     * 저장된 모든 회원을 조회하는 메서드
     * - store(HashMap)에 저장된 모든 회원을 List로 변환하여 반환
     *
     * @return 저장된 모든 회원 목록
     */
    @Override
    public List<Member> findAll() {
        // HashMap의 모든 value(Member 객체)를 새로운 ArrayList로 변환하여 반환
        return new ArrayList<>(store.values());
    }

    /**
     * 저장된 모든 회원 정보를 삭제하는 메서드
     * - 테스트 실행 후 데이터 정리를 위해 사용됨
     */
    public void clearStore() {
        store.clear();
    }
}
