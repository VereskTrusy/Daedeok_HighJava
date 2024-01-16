package kr.or.ddit.basic;


import kr.or.ddit.basic.util.JDBCUtil;
import kr.or.ddit.basic.util.JDBCUtil3;

import java.sql.*;
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

    Scanner scan = new Scanner(System.in); //  키보드 입력 읽기

    public static void main(String[] args) {
        new JDBCTest07().start(); // 프로그램 실행
    }

    /**
     * 프로그램 시작
     */
    private void start() {
        while (true) {
            // 메뉴 출력
            System.out.println("---------------------------------------------------");
            System.out.println("1. 자료 추가");
            System.out.println("2. 자료 삭제");
            System.out.println("3. 자료 수정");
            System.out.println("4. 전체 자료 출력");
            System.out.println("5. 개별 자료 수정");
            System.out.println("0. 작업 종료");
            System.out.println("---------------------------------------------------");

            // 메뉴 입력 받기
            int menuNo = scan.nextInt();
            scan.nextLine(); // 버퍼 비우기

            switch (menuNo) {
                case 1 :
                    insertMember();
                    break;
                case 2 :
                    deleteMember();
                    break;
                case 3 :
                    updateInfoAll();
                    break;
                case 4 :
                    selectAllMymember();
                    break;
                case 5 :
                    updateInfo();
                    break;
                case 0 :
                    return;
                default :
                    System.out.println("올바른 입력이 아닙니다.");
                    System.out.println("메뉴를 다시 선택 해주세요.");
                    break;
            }
        }

    }


    /**
     * 5. 자료 수정 2
     */
    private void updateInfo() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        System.out.println("5. 개별 자료 수정 입니다.");

        String inputId; // 사용자로부터 입력받은 아이디
        System.out.println("수정 할 아이디를 입력하세요 > ");
        inputId = scan.nextLine();

        while(true) {
            try{
                String sql = " SELECT COUNT(MEM_ID) AS IDCHK " +
                        " FROM MYMEMBER                 " +
                        " WHERE 1=1                     " +
                        " AND MEM_ID = TRIM(?)          ";
                // DB연결
                conn = JDBCUtil3.getConnection();

                // 변수 세팅
                ps = conn.prepareStatement(sql);
                ps.setString(1, inputId);

                // rs 검사
                rs = ps.executeQuery();

                if(rs.next()) {
                    int result = rs.getInt("IDCHK");

                    if(result > 0){
                        System.out.println("계정 확인이 완료됐습니다.");
                        break;
                    } else {
                        System.out.println("입력하신 아이디와 암호를 다시 확인 해주세요.");
                        //continue;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
                if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
                if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
            }
        }


        int fieldNum;
        String updateField = null;
        String updateTitle = null;

        do{
            System.out.println();
            System.out.println("회원님의 정보 중 수정할 항목을 선택하세요");
            System.out.println("1.비밀번호수정  2.이름수정  3.전화번호수정  4.주소수정");
            System.out.println("5.나가기");

            // 어떤 메뉴 수정할지 선택
            fieldNum = scan.nextInt();
            scan.nextLine(); // 버퍼 비우기

            switch (fieldNum) {
                case 1 :
                    updateField = "MEM_PASS";
                    updateTitle = "비밀번호";
                    break;
                case 2 :
                    updateField = "MEM_NAME";
                    updateTitle = "회원명";
                    break;
                case 3 :
                    updateField = "MEM_TEL";
                    updateTitle = "전화번호";
                    break;
                case 4 :
                    updateField = "MEM_ADDR";
                    updateTitle = "주소";
                    break;
                default :
                    System.out.println("수정 항목을 잘못 선택하셨습니다. 다시 선택하세요.");
                    break;
            }
        }while (fieldNum < 1 || fieldNum > 4);

        System.out.println();
        System.out.print("새로운 " + updateTitle + " > ");
        String updateData = scan.nextLine();

        try{
            conn = JDBCUtil3.getConnection();
            String sql = " UPDATE MYMEMBER SET "+updateField+" = ? " +
                         " WHERE MEM_ID = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, updateData);
            ps.setString(2, inputId);

            int cnt = ps.executeUpdate();

            if(cnt > 0) System.out.println("수정 작업 성공");
            else System.out.println("수정 작업 실패");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //if(rs != null) try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(ps != null) try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
    } // updateInfo() 끝

    /**
     * 4. 전체 자료 출력
     */
    private void selectAllMymember() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        System.out.println("4. 전체 자료 출력 입니다.");

        try {
            String sql = " SELECT               " +
                         "    MEM_ID AS ID,     " +
                         "    MEM_NAME AS NAME, " +
                         "    MEM_TEL AS TEL,   " +
                         "    MEM_ADDR AS ADDR  " +
                         " FROM MYMEMBER        " +
                         " ORDER BY MEM_ID ASC  ";
            // DB 연결
//            conn = JDBCUtil.getConnection();
//            conn = JDBCUtil2.getConnection(); // properties 파일 읽어서 사용해봄 하지만 이건 옛날 방식임
            conn = JDBCUtil3.getConnection(); // ResourceBundle 객체를 이용한 Properties 읽기
            // ps 세팅
            ps = conn.prepareStatement(sql);
            // 결과
            rs = ps.executeQuery();

            // 컬럼명 찍기위한 ResultSetMetaData
            rsmd = rs.getMetaData();
            boolean columnFlag = true;

            while(rs.next()) {
                // 컬럼명 출력
                if(columnFlag){
                    System.out.println("----------------------------------------------");
                    System.out.print(rsmd.getColumnLabel(1) + "\t\t");
                    System.out.print(rsmd.getColumnLabel(2) + "\t\t");
                    System.out.print(rsmd.getColumnLabel(3) + "\t\t\t");
                    System.out.println(rsmd.getColumnLabel(4));
                    System.out.println("----------------------------------------------");
                    columnFlag = false;
                }
                // 데이터 출력
                System.out.print(rs.getString("ID") + "\t");
                System.out.print(rs.getString("NAME") + "\t");
                System.out.print(rs.getString("TEL") + "\t");
                System.out.println(rs.getString("ADDR"));
                System.out.println("----------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(ps != null) try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * 3. 자료 수정
     */
    private void updateInfoAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println("3. 자료 수정 입니다.");

        String inputId = ""; // 사용자로부터 입력받은 아이디
        String inputPass = ""; // 사용자로부터 입력받은 암호

        // 사용자로부터 아이디와 패스워드를 입력받아 DB조회 후 일치 여부 검사
        while(true){
            try {
                System.out.println("수정 할 아이디를 입력하세요 > ");
                inputId = scan.nextLine();
                System.out.println("패스워드를 입력하세요 > ");
                inputPass = scan.nextLine();

                String sql = " SELECT COUNT(MEM_ID) AS IDCHK " +
                             " FROM MYMEMBER                 " +
                             " WHERE 1=1                     " +
                             " AND MEM_ID = TRIM(?)          " +
                             " AND MEM_PASS = TRIM(?)        ";

                // DB연결
                conn = JDBCUtil3.getConnection();

                // 변수 세팅
                ps = conn.prepareStatement(sql);
                ps.setString(1, inputId);
                ps.setString(2, inputPass);

                // rs 검사
                rs = ps.executeQuery();

                if(rs.next()) {
                    int result = rs.getInt("IDCHK");

                    if(result > 0){
                        System.out.println("계정 확인이 완료됐습니다.");
                        break;
                    } else {
                        System.out.println("입력하신 아이디와 암호를 다시 확인 해주세요.");
                        continue;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
                if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
                if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
            }
        }


        try {
            System.out.println("수정하실 정보를 입력해 주세요.(아이디 수정 불가)");
            System.out.println("비밀번호를 입력하세요 > ");
            String changePass = scan.nextLine();
            System.out.println("이름을 입력하세요 > ");
            String changeName = scan.nextLine();
            System.out.println("전화번호를 입력하세요 > ");
            String changeTel = scan.nextLine();
            System.out.println("주소를 입력하세요 > ");
            String changeAddr = scan.nextLine();

            String sql = " UPDATE MYMEMBER SET " +
                         "     MEM_PASS = TRIM(?) ,   " +
                         "     MEM_NAME = TRIM(?) ,   " +
                         "     MEM_TEL = TRIM(?) ,    " +
                         "     MEM_ADDR = TRIM(?)     " +
                         " WHERE 1=1                  " +
                         " AND MEM_ID = ?             " +
                         " AND MEM_PASS = ?           ";

            // DB연결
            conn = JDBCUtil3.getConnection();
            // 변수 세팅
            ps = conn.prepareStatement(sql);
            ps.setString(1, changePass);
            ps.setString(2, changeName);
            ps.setString(3, changeTel);
            ps.setString(4, changeAddr);
            ps.setString(5, inputId);
            ps.setString(6, inputPass);

            // 결과 검사
            int result = ps.executeUpdate();

            if(result > 0) {
                System.out.println("정보 수정이 완료됐습니다.");
            } else {
                System.out.println("이게 뭔 상황?");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }
    }

    /**
     * 2. 자료 삭제
     */
    private void deleteMember() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println("2. 자료 삭제 입니다.");
        String inputId = ""; // 사용자로부터 입력받은 아이디
        String inputPass = ""; // 사용자로부터 입력받은 암호

        // 사용자로부터 아이디와 패스워드를 입력받아 DB조회 후 일치 여부 검사
        try {
            while(true){
                // 아이디와 패스워드 입력 받기
                System.out.println("삭제 할 아이디를 입력하세요 > ");
                inputId = scan.nextLine();
                System.out.println("패스워드를 입력하세요 > ");
                inputPass = scan.nextLine();

                // 입력된 계정의 정보 일치여부 조회한다
                String sql = " SELECT COUNT(MEM_ID) AS IDCHK " +
                        " FROM MYMEMBER                      " +
                        " WHERE 1=1                          " +
                        " AND MEM_ID = TRIM(?)               " +
                        " AND MEM_PASS = TRIM(?)             ";

                conn = JDBCUtil3.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, inputId);
                ps.setString(2, inputPass);
                rs = ps.executeQuery();

                if(rs.next()){
                    int idChk = rs.getInt("IDCHK");

                    if( idChk > 0 ){
                        // 아이디와 암호가 일치함
                        System.out.println("계정 확인이 완료됐습니다.");
                        break;
                    } else {
                        // 아이디와 암호가 일치하지 않음
                        System.out.println("입력하신 아이디와 암호를 다시 확인 해주세요.");
                        continue;
                    } // 오류인 상황이 있을까?
                } else {
                    System.out.println("이게 뭔 상황?");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }


        // 입력된 아이디 삭제처리 진행한다.
        try{
            // 상위 선언된 지역변수 사용
            // String inputId; // 사용자로부터 입력받은 아이디
            // String inputPass; // 사용자로부터 입력받은 암호

            String SQL = " DELETE FROM MYMEMBER " +
                         " WHERE 1=1            " +
                         " AND MEM_ID = ?       " +
                         " AND MEM_PASS = ?     ";

            conn = JDBCUtil3.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, inputId);
            ps.setString(2, inputPass);
            int result = ps.executeUpdate();

            if(result > 0) {
                // 삭제 완료된 상태
                System.out.println(inputId + " 삭제가 완료되었습니다.");
            } else {
                // 삭제처리 안된 상태
                System.out.println("삭제 처리가 완료되지 못했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }
    } // deleteMember() 끝

    /**
     * 1. 자료 추가
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

        String inputId = "";
        // 중복이 아닌 아이디를 입력할 때 까지 반족
        while (true){
            try {
                // 회원 아이디 중복값 존재 확인
                System.out.println("아이디를 입력 하세요 > ");
                inputId = scan.nextLine();

                conn = JDBCUtil3.getConnection();
                String sql = " SELECT COUNT(MEM_ID) AS IDCHK " +
                        " FROM MYMEMBER " +
                        " WHERE 1=1 " +
                        " AND MEM_ID = TRIM( ? ) ";
                ps = conn.prepareStatement(sql);
                ps.setString(1, inputId);
                rs = ps.executeQuery();

                if(rs.next()) {
                    int count = rs.getInt("IDCHK");
                    if(count > 0){ // 가능
                        System.out.println("이미 존재하는 아이디 입니다.");
                        System.out.println("다시 입력해주세요.");
                    } else {
                        System.out.println("사용 가능한 ID입니다.");
                        break;
                    }
                } else {
                    System.out.println("오류가 발생 했습니다.");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
                if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
                if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
            }
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
            conn = JDBCUtil3.getConnection();
            String SQL = " INSERT INTO MYMEMBER (  MEM_ID, MEM_PASS, MEM_NAME, MEM_TEL, MEM_ADDR) " +
                         " VALUES( ?, ?, ?, ?, ? ) ";

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
        } finally {
            //if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }
    }
}
