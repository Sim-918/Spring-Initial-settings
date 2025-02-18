package com.example.demo.controller;


import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // MemberService를 컨트롤러에서 사용하기 위한 필드 선언
    private MemberService memberService;

    // @Autowired를 사용하여 스프링이 MemberService 객체를 자동으로 주입(DI)
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService; // 생성자를 통해 의존성을 주입받음 (생성자 주입 방식)
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member=new Member();
        member.setName(form.getName());

//        System.out.println("member = "+member.getName());

        memberService.join(member);

        return "redirect:/";
    }

//  단지 메모리에 담고 있는 데이터임! (과거 MemoryMemberRepository 사용 시)
//  하지만 현재는 DB 기반(JdbcMemberRepository) 저장소를 사용하므로 DB에서 데이터를 조회함
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members=memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
