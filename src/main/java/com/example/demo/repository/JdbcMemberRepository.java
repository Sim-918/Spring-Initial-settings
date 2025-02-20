package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//순수 JDBC코드이다. (jdbcTemplatememberrepository 코드가 jdbctemplate를 사용하는 코드)
// JdbcMemberRepository는 MemberRepository 인터페이스를 구현하여
// JDBC를 통해 데이터베이스와 직접 연동하는 클래스

public class JdbcMemberRepository implements MemberRepository {

    // DataSource를 통해 DB 커넥션을 관리
    private final DataSource dataSource;

    // DataSource를 생성자 주입 (의존성 주입 방식 사용)
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 회원 정보 저장 메서드
     * @param member 저장할 회원 객체
     * @return 저장된 회원 객체 (ID 포함)
     */
    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)"; // SQL 쿼리 (회원 이름만 저장)

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // DB 연결 획득
            conn = getConnection();
            // Statement.RETURN_GENERATED_KEYS를 사용하여 자동 생성된 키(ID)를 반환받을 수 있도록 설정
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // SQL 쿼리의 `?` 부분에 값 삽입
            pstmt.setString(1, member.getName());

            // SQL 실행 (데이터 삽입)
            pstmt.executeUpdate();
            // 자동 생성된 ID 조회
            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                member.setId(rs.getLong(1)); // 조회된 ID를 Member 객체에 설정
            } else {
                throw new SQLException("id 조회 실패"); // ID 조회 실패 시 예외 발생
            }
            return member; // 저장된 회원 객체 반환
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            // 사용한 리소스 (Connection, PreparedStatement, ResultSet) 해제
            close(conn, pstmt, rs);
        }
    }

    /**
     * ID로 회원을 조회하는 메서드
     * @param id 조회할 회원의 ID
     * @return 조회된 회원 객체 (Optional로 감싸서 반환)
     */
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?"; // ID를 조건으로 회원 조회

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id); // ID 값 바인딩

            rs = pstmt.executeQuery(); // SQL 실행

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member); // 조회된 회원 정보를 Optional로 반환
            } else {
                return Optional.empty(); // 조회된 결과가 없을 경우 빈 Optional 반환
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    /**
     * 모든 회원 정보를 조회하는 메서드
     * @return 데이터베이스에 저장된 모든 회원 리스트
     */
    @Override
    public List<Member> findAll() {
        String sql = "select * from member"; // 모든 회원 조회 쿼리

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();
            while (rs.next()) { // 조회된 모든 회원 데이터를 리스트에 추가
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }

            return members; // 회원 리스트 반환
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    /**
     * 이름으로 회원을 조회하는 메서드
     * @param name 조회할 회원의 이름
     * @return 조회된 회원 객체 (Optional로 감싸서 반환)
     */
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?"; // 이름으로 회원 조회 쿼리

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name); // 이름 값 바인딩

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    /**
     * 데이터베이스 커넥션을 가져오는 메서드
     * @return Connection 객체
     */
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    /**
     * 데이터베이스 연결 리소스를 안전하게 해제하는 메서드
     * @param conn Connection 객체
     * @param pstmt PreparedStatement 객체
     * @param rs ResultSet 객체
     */
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 데이터베이스 연결을 해제하는 메서드
     * @param conn Connection 객체
     * @throws SQLException SQL 예외 발생 시 처리
     */
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}


