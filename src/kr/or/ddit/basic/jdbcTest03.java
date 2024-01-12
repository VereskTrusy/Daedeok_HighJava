package kr.or.ddit.basic;

import oracle.jdbc.driver.OracleDriver;

import java.sql.*;
import java.util.Scanner;

/*
    문제 2) LPROD_ID값을 2개 입력 받아서 두 값 중 작은 값부터 큰값 사이의 자료들을 출력하시오.
    예) 3, 5 입력 시 -> 3 ~ 5까지의 값 모두 조회
 */
public class jdbcTest03 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner scan = new Scanner(System.in);
        String sql;
        int minData;
        int maxData;

        try {
            // 데이터 입력 받기
            System.out.println("1. LPRID_ID > ");
            minData = scan.nextInt();

            System.out.println("2. LPRID_ID > ");
            maxData = scan.nextInt();

            if(minData > maxData) {
                int temp = maxData;
                maxData = minData;
                minData = temp;
            }

            // SQL문 만들기
            //sql = "SELECT LPROD_ID AS ID, LPROD_GU AS GU, LPROD_NM AS NM FROM LPROD WHERE 1=1 AND LPROD_ID >= " + minData + " AND LPROD_ID <= " + maxData;
            sql = " SELECT LPROD_ID AS ID " +
                  "      , LPROD_GU AS GU " +
                  "      , LPROD_NM AS NM " +
                  " FROM LPROD " +
                  " WHERE 1=1 " +
                  " AND LPROD_ID BETWEEN " + minData + " AND " + + maxData;

            // 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 작업
            conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.142.32:1521:xe", "JMW87", "java");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // 데이터 널 체크
            boolean dataIsNull = true;

            // 출력
            while (rs.next()) {
                dataIsNull = false;
                System.out.println("ID : " + rs.getInt("ID"));
                System.out.println("GU : " + rs.getString("GU"));
                System.out.println("NM : " + rs.getString("NM"));
                System.out.println("-----------------------------------------------------------");
            }

            // 결과 널이면
            if(dataIsNull) {
                System.out.println("검색된 결과가 없습니다.");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs  ) try { rs.close();   } catch (SQLException e ) { e.printStackTrace(); }
            if(null != stmt) try { stmt.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }


    }
}
