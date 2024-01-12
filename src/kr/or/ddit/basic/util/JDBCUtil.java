package kr.or.ddit.basic.util;

import java.sql.*;

/**
 * JDBC 드라이버를 로딩하고 DB에 접속하여 Connection 객체를 반환하는 메서드
 *
 */
public class JDBCUtil {
    // 인스턴스 초기화 블록
    {
        // 초기화 할 내용
    }
    
    // 정적 초기화 블록
    static{
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("OracleDriver 로딩 실패");
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JMW87", "java");
        } catch (SQLException e) {
            System.out.println("DB Connection 실패");
            e.printStackTrace();
        }
        return conn;
    }


}
