package kr.or.ddit.basic;

import com.sun.org.apache.xalan.internal.xsltc.dom.CurrentNodeListFilter;

import java.sql.*;

/*

    JDBC(Java DataBase Connectivity) -> java에서 DB자료를 처리하는 라이브러리

    - DB자료 처리 순서
    1. 드라이버 로딩 -> JDBC라이브러리를 사용할 수 있도록 메모리로 읽어들이는 작업
       Class.forName("oracle.jdbc.driver.OracleDriver")
       다른 DB일 경우 해당 관련 찾아보고 드라이버 로드 할 것.
    2. DB에 접속하기 -> 접속이 완료되면 Connection 객체가 생성되어 반환된다.
       DriverManager.getConnection()메서드를 이용한다.
    3. 질의(query) -> SQL문장을 DB서버로 보내서 결과를 얻어온다.
       (Stagement객체나 PreparedStatement객체를 이용하여 작업한다.)
    4. 결과 처리 -> 질의 결과를 받아서 원하는 작업을 수행한다.
       1) SQL문장이 'SELECT'문일 경우에는 select한 결과가 ResultSet객체에 저장되어 반환된다.
       2) SQL문장이 'SELECT'문이 아닐경우 (INSERT, UPDATE, DELETE 등)에는
          정수값을 반환한다.(이 정수값은 보통 실행에서 성공한 레코드 수를 말한다.)
    5. 사용했던 자원 반납하기 -> 각 객체의 close()메서드를 이용한다.

 */
public class JDBCTest01 {
    public static void main(String[] args) {
        // DB작업에 필요한 객체 변수 선언
        Connection conn = null;
        Statement stmt = null; // 지금은 Statement 클래스 사용 다음에 PreparedStatement 사용
        ResultSet rs = null;

        try {
            // Step 1. 드라이버 로딩
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Step 2. DB연결
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "JMW87", "java");

            // Step 3. 질의
            // Step 3-1. SQL문 작성
            String sql = "SELECT LPROD_ID AS ID, LPROD_GU AS GU, LPROD_NM AS NM FROM LPROD";

            // Step 3-2. Statement객체 생성 -> 질의하는 객체 생성 (Connection객체를 이용하여 생성한다.)
            stmt = conn.createStatement();

            // Step 3-3. SQL문장을 DB서버로 보내서 결과를 얻어온다.
            //           (실행할 SQL문장이 'select'문이기 때문에 결과가 ResultSet객체에 저장되어 반환된다.
            rs = stmt.executeQuery(sql);

            // Step 4. 결과 처리하기 -> 한 레코드씩 화면에 출력
            //         ResultSet객체에 저장된 데이터를 차례로 꺼내오려면 반복문과 next()메서드를 이용하여 처리한다.
            System.out.println("--- 쿼리문 처리결과 ---");

            // ResultSet객체의 next()메서드
            // -> ResultSet객체의 데이터를 가리키느 포인터는 다음번째 레코드로 이동시키고 그 곳에 데이터가 있으면 true, 없으면 false를 반환한다.
            while (rs.next()){
                // 포인터가 가리키는 곳의 자료를 가져오는 방법
                // 형식 1) ResultSet객체.get자료형이름("컬럼명" 또는 "alias명")
                // 형식 2) ResultSet객체.get자료형이름(컬럼번호)
                System.out.println("LPROD_ID : " + rs.getInt("ID")); // get자료형("컬럼명")에서 컬럼명은 대소문자 구분안함
                System.out.println("LPROD_GU : " + rs.getString(2));
                System.out.println("LPROD_NM : " + rs.getString("NM"));
                System.out.println("-----------------------------------------------------------");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5. 자원 반납
            if(rs   != null) try { rs.close();   } catch (SQLException e) { }
            if(stmt != null) try { stmt.close(); } catch (SQLException e) { }
            if(conn != null) try { conn.close(); } catch (SQLException e) { } // Connection객체는 반드시 닫아줘야한다.
            // DB접속 가능 회선의 수는 정해져 있기 때무닝ㅁ
        }


    }
}
