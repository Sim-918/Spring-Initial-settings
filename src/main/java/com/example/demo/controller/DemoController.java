package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DemoController {
//    1. 정적 컨텐츠 (hello.html 파일을 그대로 반환)
//    http://localhost:8080/
    @GetMapping("hello") // /hello 경로로 GET 요청이 오면 이 메서드 실행
    public String demo(Model model) {
        model.addAttribute("data", "Hello!!"); // "data"라는 이름으로 "Hello!!" 값을 뷰에 전달
        return "hello"; // 뷰 이름 "hello"를 반환 (templates 폴더에서 hello.html 파일을 찾음)
    }
//    2. MVC방식(hello-template.html 파일을 랜더링하여 html반환)
//    http://localhost:8080/hello-mvc?name=simsungbo!
    @GetMapping("hello-mvc") // /hello-mvc 경로로 GET 요청이 오면 이 메서드 실행
    public String helloMvc(@RequestParam(value="name") String name, Model model) {
        model.addAttribute("name", name);  // "name"이라는 이름으로 name 변수 값을 뷰에 전달
        return "hello-template"; // 뷰 이름 "hello-template"을 반환 (templates 폴더에서 hello-template.html 파일을 찾음)
    }
//    3. 문자 반환 (HTTP 응답 본문에 문자열 데이터를 직접 담아서 반환)
//    http://localhost:8080/hello-string?name=simsungbo
    @GetMapping("hello-string") // /hello-string 경로로 GET 요청이 오면 이 메서드 실행
    @ResponseBody // @ResponseBody : HTTP 응답 본문에 메서드 반환 값을 직접 담음
    public String helloString(@RequestParam("name") String name) { // URL에서 "name" 파라미터 값을 가져와 name 변수에 저장
        return "hello "+name; // "hello " + name 값을 HTTP 응답 본문에 담아 반환
    }
//  4. 객체 반환 (HTTP 응답 본문에 JSON 형태로 객체 데이터를 담아서 반환)
//    http://localhost:8080/hello-api?name=simsungbo
    @GetMapping("hello-api") // /hello-api 경로로 GET 요청이 오면 이 메서드 실행
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name); // Hello 객체의 name 필드에 name 변수 값 설정
        return hello; // Hello 객체를 HTTP 응답 본문에 담아 반환 (JSON 형태로 변환됨)
    }
//    Hello 객체 클래스(데이터를 담는 용도)
    static class  Hello {
        private String name;
//      name 필드 값을 반환하는 getter 메서드
        public String getName() {
            return name;
        }
//      name필드값을 설정하는 setter 메서드
        public void setName(String name) {
            this.name = name;
        }
    }

}