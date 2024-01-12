package kr.or.ddit.basic;


import kr.or.ddit.basic.util.JDBCUtil;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCTest07 {
    /*
        -- MYMEMBER 테이블 만들기
        CREATE TABLE MYMEMBER(
            MEM_ID VARCHAR2(30) NOT NULL,
            MEM_PASS VARCHAR2(30) NOT NULL,
            MEM_NAME VARCHAR2(50) NOT NULL,
            MEM_TEL VARCHAR2(14) NOT NULL,
            MEM_ADDR VARCHAR2(100) NOT NULL,
            CONSTRAINT PK_MYMEMBER PRIMARY KEY(MEM_ID)
        )

        회원을 관리하는 프로그램을 작성하시오.
        ( MYMEMBER테이블 이용 )

        아래 메뉴의 기능을 모두 구현하시오.
        ( CRUD 기능 구현 연습 )

        메뉴 에시)
        ---------------------------------------------------
        1. 자료 추가
        2. 자료 삭제
        3. 자료 수정
        4. 전체 자료 출력
        5. 작업 종료
        ---------------------------------------------------

        조건
        1) 자료 추가에서 '회원ID'는 중복되지 않는다. (중복되면 다시 입력 받는다.)
        2) 자료 삭제는 '회원ID'를 입력 받아서 처리한다.
        3) 자료 수정에서 '회원ID'는 변경되지 않는다.
     */
    public static void main(String[] args) {
        new JDBCTest07().start(); // 프로그램 실행
    }

    Scanner scan = new Scanner(System.in); //  키보드 입력 읽기
    private void start() {
// 네번째 깃 테스트
        while (true) {
            // 메뉴 출력
            System.out.println("---------------------------------------------------");
            System.out.println("1. 자료 추가");
            System.out.println("2. 자료 삭제");
            System.out.println("3. 자료 수정");
            System.out.println("4. 전체 자료 출력");
            System.out.println("5. 작업 종료");
            System.out.println("---------------------------------------------------");

            // 메뉴 입력 받기
            int menuNo = scan.nextInt();

            switch (menuNo) {
                case 1 :
                    insertMember();
                    break;
                case 2 :
                    deleteMember();
                    break;
                case 3 :
                    updateMember();
                    break;
                case 4 :
                    break;
                case 5 :
                    return;
                default :
                    System.out.println("올바른 입력이 아닙니다.");
                    System.out.println("메뉴를 다시 선택 해주세요.");
                    break;
            }
        }

    }

    /**
     * 자료 수정
     */
    private void updateMember() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println("2. 자료 삭제 입니다.");

        scan.nextLine(); // 버퍼비우기
        while (true) {
            try {
                System.out.println("수정 할 아이디를 입력하세요.");
                String inputId = scan.nextLine();

                String sql = " SELECT COUNT(MEM_ID) AS IDCHK " +
                             " FROM MYMEMBER " +
                             " WHERE 1=1 " +
                             " AND MEM_ID = TRIM( ? ) ";
                conn = JDBCUtil.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, inputId);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 자료 삭제
     */
    private void deleteMember() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println("2. 자료 삭제 입니다.");

        scan.nextLine(); // 버퍼비우기

        // 아 맞다 아이디 체크 ㅅㅂ
        try {
            System.out.println("삭제 할 아이디를 입력하세요 > ");
            String inputId = scan.nextLine();

            String SQL = " DELETE FROM MYMEMBER " +
                         " WHERE MEM_ID = ? ";

            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, inputId);
            int result = ps.executeUpdate();

            if(result > 0){
                System.out.println("삭제가 완료되었습니다.");
            } else {
                System.out.println("오류가 발생 했습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 자료 추가
     */
    private void insertMember() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println("1. 자료 추가 입니다.");
        // 회원 아이디 맥스값 가져오기
//        String maxId;
//        try{
//            conn = JDBCUtil.getConnection();
//            System.out.println("DB에 연결되었습니다.");
//
//            String SQL = " SELECT MAX(TO_NUMBER(MEM_ID)) + 1 AS MAXID " +
//                         " FROM MYMEMBER ";
//            ps = conn.prepareStatement(SQL);
//            rs = ps.executeQuery();
//            if(rs.next()){
//                maxId = rs.getString("MAXID");
//                System.out.println("maxId : " + maxId);
//
//            } else {
//                System.out.println("ID 조회에 실패 했습니다.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if(rs != null ){ try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }}
//            if(ps != null ){ try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }}
//            if(conn != null ){ try { conn.close(); } catch(SQLException e) { e.printStackTrace(); }}
//        }

        scan.nextLine(); //버퍼비우기

        String inputId = "";
        try {
            // 중복이 아닌 아이디를 입력할 때 까지 반족
            while (true){
                try {
                    // 회원 아이디 중복값 존재 확인
                    System.out.println("아이디를 입력 하세요 > ");
                    inputId = scan.nextLine();

                    conn = JDBCUtil.getConnection();
                    String SQL = " SELECT COUNT(MEM_ID) AS IDCHK " +
                                 " FROM MYMEMBER " +
                                 " WHERE 1=1 " +
                                 " AND MEM_ID = TRIM( ? ) ";
                    ps = conn.prepareStatement(SQL);
                    ps.setString(1, inputId);
                    rs = ps.executeQuery();

                    if(rs.next()) {
                        int count = rs.getInt("IDCHK");
                        if(count > 0){ // 가능
                            System.out.println("이미 존재하는 아이디 입니다.");
                            System.out.println("다시 입력해주세요.");
                            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
                            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
                        } else {
                            System.out.println("사용 가능한 ID입니다.");
                            break;
                        }
                    } else {
                        System.out.println("오류가 발생 했습니다.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        // 회원 정보 DB 삽입
        // 나머지 정보 입력 받기
        System.out.println("비밀번호를 입력하세요 > ");
        String inputPassword = scan.nextLine();
        System.out.println("성명을 입력하세요 > ");
        String inputName = scan.nextLine();
        System.out.println("전화번호를 입력하세요 > ");
        String inputTel = scan.nextLine();
        System.out.println("주소를 입력하세요 > ");
        String inputAddr = scan.nextLine();

        try {
            conn = JDBCUtil.getConnection();
            String SQL = " INSERT INTO MYMEMBER ( " +
                         "    MEM_ID " +
                         "    , MEM_PASS " +
                         "    , MEM_NAME " +
                         "    , MEM_TEL " +
                         "    , MEM_ADDR) " +
                         " VALUES( " +
                         "      ? " +
                         "    , ? " +
                         "    , ? " +
                         "    , ? " +
                         "    , ? ) ";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, inputId);
            ps.setString(2, inputPassword);
            ps.setString(3, inputName);
            ps.setString(4, inputTel);
            ps.setString(5, inputAddr);
            int result = ps.executeUpdate();

            if(result > 0) {
                System.out.println("성공적으로 입력되었습니다.");
            } else {
                System.out.println("오류가 발생 했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
