package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// JdbcTemplate을 사용하여 데이터베이스 연동을 처리하는 회원 저장소 클래스
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate; // JDBC 작업을 편리하게 처리하는 객체

    // @Autowired 생략 가능 (스프링에서 생성자가 하나만 있으면 자동 주입됨)
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource); // DataSource를 사용하여 JdbcTemplate 객체 생성
    }

    /**
     * 회원 저장 메서드
     * @param member 저장할 회원 객체
     * @return 저장된 회원 객체
     */
    @Override
    public Member save(Member member) {
        // SimpleJdbcInsert를 사용하여 SQL INSERT를 편리하게 실행
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member") // 테이블 이름 설정
                .usingGeneratedKeyColumns("id"); // 자동 생성 키 컬럼 지정

        // 저장할 데이터를 Map에 담아 전달
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        // INSERT 실행 후 생성된 키(ID)를 받아와서 설정
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue()); // 생성된 ID 설정
        return member; // 저장된 회원 객체 반환
    }

    /**
     * ID로 회원 조회 메서드
     * @param id 조회할 회원의 ID
     * @return 조회된 회원 객체 (Optional로 감싸서 반환)
     */
    @Override
    public Optional<Member> findById(Long id) {
        // SQL 실행 후 결과를 Member 객체로 매핑하여 리스트로 반환
        List<Member> result = jdbcTemplate.query(
                "SELECT * FROM member WHERE id = ?", memberRowMapper(), id);
        return result.stream().findAny(); // 첫 번째 결과 반환 (없으면 Optional.empty())
    }

    /**
     * 이름으로 회원 조회 메서드
     * @param name 조회할 회원의 이름
     * @return 조회된 회원 객체 (Optional로 감싸서 반환)
     */
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query(
                "SELECT * FROM member WHERE name = ?", memberRowMapper(), name);
        return result.stream().findAny(); // 첫 번째 결과 반환 (없으면 Optional.empty())
    }

    /**
     * 모든 회원 목록 조회 메서드
     * @return 모든 회원을 리스트로 반환
     */
    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member", memberRowMapper());
    }

    /**
     * ResultSet을 Member 객체로 변환하는 RowMapper
     * 람다 표현식을 사용하여 간결하게 구현
     * @return Member 객체를 매핑하는 RowMapper
     */
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id")); // ID 값 설정
            member.setName(rs.getString("name")); // 이름 값 설정
            return member; // 매핑된 Member 객체 반환
        };
    }
}
