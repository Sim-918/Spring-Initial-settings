package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/") // 루트 경로 (http://localhost:8080/) 요청이 오면 home() 메서드를 실행
    public String home() {
        return "home"; // "home"이라는 이름의 뷰(HTML 파일)를 반환 (템플릿 엔진 사용)
    }

/*
뷰 파일(HTML) 로딩 순서
스프링 부트에서는 기본적으로 뷰 파일을 찾을 때 특정 경로에서 검색합니다.

1️ 먼저 src/main/resources/templates 폴더를 확인
    -templates/home.html 파일이 있으면 해당 파일을 렌더링
2️ templates 폴더에 파일이 없으면 static 폴더를 확인
    -static/home.html이 있으면 해당 파일을 반환
*/

}
