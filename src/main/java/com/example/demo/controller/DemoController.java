package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DemoController {
//    정적 컨텐츠 (파일그대로 내보내줌)
    @GetMapping("hello")
    public String demo(Model model) {
        model.addAttribute("data", "Hello!!");
        return "hello";
    }
//    MVC방식(랜더딩 된 html전달)
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value="name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
//    문자 반환
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello "+name;
    }
//  객체 반환
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class  Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}