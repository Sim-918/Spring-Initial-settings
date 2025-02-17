package com.example.demo.domain; // 회원 정보를 저장하는 도메인 패키지

//DB table
// 회원(Member) 정보를 담는 클래스
public class Member {
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
