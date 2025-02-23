package com.example.demo.domain; // 회원 정보를 저장하는 도메인 패키지

import jakarta.persistence.*;

//DB table
// 회원(Member) 정보를 담는 클래스
@Entity  // JPA에서 엔터티로 인식하여 DB 테이블과 매핑
public class Member {

    @Id //해당 필드를 primary key로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ID값을 데이터베이스가 자동으로 생성
    private Long id;  // 회원의 고유 ID
    private String name; // 회원의 이름

    // Getter: id 값을 반환 (읽기)
    public Long getId() {
        return id;
    }

    // Setter: id 값을 설정 (쓰기)
    public void setId(Long id) {
        this.id = id;
    }

    // Getter: name 값을 반환 (읽기)
    public String getName() {
        return name;
    }

    // Setter: name 값을 설정 (쓰기)
    public void setName(String name) {
        this.name = name;
    }
}
