package kr.or.ddit.basic;


import kr.or.ddit.basic.util.JDBCUtil;

import java.sql.*;
import java.util.Scanner;

/*
    문제 3) LPROD테이블에 새로운 데이터 추가하기

    LPROD_GU, LPROD_NM은 직접입력받아 처리하고,
    LPROD_ID는 현재 값들 중 가장 큰값보다 1크게 설정한다.

    입력받은 LROD_GU가 이미 등록되어 있으면 다시 입력받아서 처리한다.
 */
public class JDBCTest05 {
    public static void main(String[] args) {

        // jdbc 사용 객체들 생성
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        /*String driverName = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@192.168.142.32:1521:xe";
        String user = "JMW87";
        String pass = "java";*/

        Scanner scan = new Scanner(System.in);

        int LProdId = 0;
        String LProdGu; // 상품종류 코드
        String LProdNm; // 상품종류 명

        // LPROD_ID의 다음 값 구하기(맥스값)
        String sql = " SELECT MAX(LPROD_ID) + 1 AS MAXID " +
                     " FROM LPROD ";

        try {
            /*Class.forName(driverName);

            conn = DriverManager.getConnection(url, user, pass);*/

            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if( rs.next() ){
                LProdId = rs.getInt("MAXID"); // ID값 세팅
                System.out.println("ID값 조회에 성공했습니다.(" + LProdId + ")");
            } else {
                System.out.println("ID값 조회에 실패했습니다.");
            }

        }/* catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/ catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }


        // 상품종류 코드(LPROD_GU)를 입력 받고 중복이 있는지 조회
        while (true){

            System.out.println("등록하실 상품종류 코드를 입력하세요 > ");
            LProdGu = scan.nextLine(); // 상품종류 코드

            // id 중복 조회
            sql = " SELECT COUNT(LPROD_GU) AS IDCHK " +
                  " FROM LPROD WHERE LPROD_GU = ? ";

            int idChk = 0;

            try {
                /*Class.forName(driverName);

                conn = DriverManager.getConnection(url, user, pass);*/
                conn = JDBCUtil.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, LProdGu);
                rs = ps.executeQuery();

                if( rs.next() ) {
                    idChk = rs.getInt("IDCHK");

                    if(idChk == 0) {
                        System.out.println("사용 가능한 ID 입니다.");
                        break;
                    } else {
                        System.out.println("이미 등록되어 있는 ID 입니다.");
                        System.out.println("다시 입력해주시기 바랍니다.");
                        continue;
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }/* catch (ClassNotFoundException e) {
                e.printStackTrace();
            }*/ finally {
                if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
                if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
                if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
            }
        }


        // 상품종류 명을 입력받고 등록 시키기
        sql = " INSERT INTO LPROD(LPROD_ID, LPROD_GU, LPROD_NM) " +
              " VALUES( ?, ?, ? ) ";

        int insertResult = 0;
        try {
            System.out.println("등록하실 상품종류명을 입력하세요 > ");
            LProdNm = scan.nextLine(); // 상품종류 명

            /*Class.forName(driverName);

            conn = DriverManager.getConnection(url, user, pass);*/
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, LProdId);
            ps.setString(2,LProdGu);
            ps.setString(3, LProdNm);
            insertResult = ps.executeUpdate();

            if(insertResult > 0) {
                System.out.println("입력하신 정보 " + insertResult + " 건이 등록완료되었습니다.");
            }else{
                System.out.println("오류발생!");
            }

        }/* catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/ catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != ps) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

    }
}
