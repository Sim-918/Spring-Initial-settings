package com.example.demo;

import com.example.demo.repository.*;
import com.example.demo.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration // 이 클래스가 스프링 설정 파일임을 나타냄 (스프링 컨테이너가 관리)
public class SpringConfig {

    // 원래 JDBC API와 JDBCtemplate를 받는 dataSource였지만 jpa사용으로 주석처리
    /*private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    */

    // Autowired를 사용한 생성자 주입(스프링 방식)
//    private EntityManager em;
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    // EntityManager를 JPA가 자동으로 주입 (JPA 표준 주입)
    @PersistenceContext
    private EntityManager em;

    @Bean // 스프링 컨테이너에 MemberService 객체를 생성하여 빈(Bean)으로 등록
    public MemberService memberService() {
        return new MemberService(memberRepository()); // MemberService가 MemberRepository를 의존하도록 설정
    }

    @Bean // 스프링 컨테이너에 MemberRepository 객체를 생성하여 빈(Bean)으로 등록
    public MemberRepository memberRepository() {
        // 기존 메모리 기반 저장소 사용 코드 (주석 처리됨)
        // return new MemoryMemberRespository();

        // DB 기반 저장소(손수 JDBC 사용)로 변경 (주석 처리됨)
        // return new JdbcMemberRepository(dataSource);

        // JDBCtemplate 사용 (주석 처리됨)
        // return new JdbcTemplateMemberRepository(dataSource);

        //JPA 사용
        return new JpaMemberRepository(em);

    }

}
