package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DemoController {
    @GetMapping("hello")
    public String demo(Model model) {
        model.addAttribute("data", "Hello!!");
        return "hello";
    }
}