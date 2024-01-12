package kr.or.ddit.basic;


import java.sql.*;
import java.util.Scanner;

/*
    계좌번호 정보를 추가하는 예제

 */
public class JDBCTest04 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.142.32:1521:xe", "JMW87", "java");

            System.out.println("계좌번호 정보 추가하기");
            System.out.println("계좌번호 > ");
            String bankNo = scan.nextLine();

            System.out.println("은행명 > ");
            String bankName = scan.nextLine();

            System.out.println("예금주명 > ");
            String bankUserName = scan.nextLine();

            // Statement 객체를 사용한 처리
//            String sql = " INSERT INTO BANKINFO(BANK_NO, BANK_NAME, BANK_USER_NAME, BANK_DATE) " +
//                    " VALUES('"+bankNo+"', '"+bankName+"', '"+bankUserName+"', SYSDATE) ";
//            stmt = conn.createStatement();
//            // sql 문장이 'select'문 일 때는 excuteQuery(0메서드를 사용하낟.
//            // sql 문장이 'insert, update, delete'문 일 때는 excuteUpdate()메서드를 사용한다.
//            // 이 메서드는 반환값은 작업에 성공한 레코드 수를 반환한다.
//            int cnt = stmt.executeUpdate(sql);
//
//            if(cnt != 0) {
//                System.out.println("반환값 : " + cnt);
//            } else {
//                System.out.println("오류발생 !");
//            }



            // PrepareStatement 객체 사용해보기
            // 같은 쿼리문을 날릴 때 PrepareStatement 가 더 빠르다
            // SQL문장에 데이터가 들어갈 자리를 물음표(?)로 작성해서 처리한다.
            String sql = " INSERT INTO BANKINFO( BANK_NO, BANK_NAME, BANK_USER_NAME, BANK_DATE) " +
                         " VALUES( ?, ?, ?, SYSDATE) ";

            // PrepareStatement 객체 생성 -> 사용할 SQL문장을 매개변수에 넘겨준다.
            pstmt = conn.prepareStatement(sql);

            // SQL 문장의 물음표(?) 자리에 들어갈 데이터를 세팅한다.
            // 형식) pstmt.set자료형이름(물음표번호,데이터);
            //      물음표번호는 1부터 시작한다.
            pstmt.setString(1, bankNo);
            pstmt.setString(2, bankName);
            pstmt.setString(3, bankUserName);

            // 데이터 세팅이 완료되면 SQL문을 실행한다.
            int sucessRecordCount = pstmt.executeUpdate(); // 실행할때 sql 전달하지 않는다.

            // 검사
            if(sucessRecordCount != 0) {
                System.out.println("반환값 : " + sucessRecordCount);
            } else {
                System.out.println("오류발생 !");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != pstmt) try { pstmt.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }
    }
}
