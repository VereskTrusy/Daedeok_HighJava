package kr.or.ddit.basic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC 드라이버를 로딩하고 DB에 접속하여 Connection 객체를 반환하는 메서드
 * 반환하는 메서드로 구성된 class 만들기
 * (dbinfo.properties 파일의 내용으로 설정하기)
 */
public class JDBCUtil2 {
    private static Properties prop;
    
    // 정적 초기화 블록
    static{
        prop = new Properties();

        File f = new File("./jdcbTest/src/res/kr/or/ddit/config/dbinfo.properties");
        FileInputStream fin = null;

        try {
            fin = new FileInputStream(f);

            prop.load(fin);
            Class.forName(prop.getProperty("driver"));

//            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("OracleDriver 로딩 실패");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fin != null) { try { fin.close(); } catch ( IOException e ) { e.printStackTrace(); } }
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(prop.getProperty("url"),
                                               prop.getProperty("user"),
                                               prop.getProperty("pass"));
        } catch (SQLException e) {
            System.out.println("DB Connection 실패");
            e.printStackTrace();
        }
        return conn;
    }


}
