package kr.or.ddit.basic;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import oracle.jdbc.driver.OracleDriver;

import java.sql.*;
import java.util.Scanner;

/*
    문제 1) 사용자로부터 LPROD_ID값을 입력 받아 입력한 값보다 LPROD_ID가 큰 자료들을 출력하시오.
 */
public class jdbcTest02 {

    public static void main(String[] args) {
        // 사용할 객체 선언
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner scan = new Scanner(System.in);
        String sql; // SQL문
        String inId; // 사용자로부터 입력받은 id값

        try {
            System.out.println("ID값을 입력하세요 > ");
            inId = scan.nextLine();

            sql = "SELECT LPROD_ID AS ID, LPROD_GU AS GU, LPROD_NM AS NM FROM LPROD WHERE 1=1 AND LPROD_ID > " + inId;

            Class.forName("oracle.jdbc.driver.OracleDriver"); // 드라이버 로드

            conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.142.32:1521:xe", "JMW87", "java");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            boolean dataCheck = false;
            System.out.println("--- 쿼리문 처리결과 ---");
            while (rs.next()) {
                dataCheck = true;
                System.out.println("ID : " + rs.getInt("ID"));
                System.out.println("GU : " + rs.getString("GU"));
                System.out.println("NM : " + rs.getString("NM"));
                System.out.println("-----------------------------------------------------------");
            }
            if(!dataCheck){
                System.out.println("검색된 데이터가 없습니다.");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != stmt) try { stmt.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }
    }
}
