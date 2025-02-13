package com.example.demo.controller;


// 회원 정보를 입력받기 위한 폼 클래스
public class MemberForm {
    private String name; // 사용자가 입력한 회원 이름을 저장하는 변수

    // Getter: 입력된 name 값을 반환 (읽기)
    public String getName() {
        return name;
    }
    // Setter: 입력된 name 값을 설정 (쓰기)
    public void setName(String name) {
        this.name = name;
    }
}
