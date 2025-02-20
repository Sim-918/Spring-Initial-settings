package com.example.demo;

import com.example.demo.repository.JdbcMemberRepository;
import com.example.demo.repository.JdbcTemplateMemberRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemoryMemberRespository;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration // 이 클래스가 스프링 설정 파일임을 나타냄 (스프링 컨테이너가 관리)
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean // 스프링 컨테이너에 MemberService 객체를 생성하여 빈(Bean)으로 등록
    public MemberService memberService() {
        return new MemberService(memberRepository()); // MemberService가 MemberRepository를 의존하도록 설정
    }

    @Bean // 스프링 컨테이너에 MemberRepository 객체를 생성하여 빈(Bean)으로 등록
    public MemberRepository memberRepository() {
        // 기존 메모리 기반 저장소 사용 코드 (주석 처리됨)
        // return new MemoryMemberRespository();

        // DB 기반 저장소(손수 JDBC 사용)로 변경
        // return new JdbcMemberRepository(dataSource);

        // JDBCtemplate 사용
        return new JdbcTemplateMemberRepository(dataSource);

    }

}
