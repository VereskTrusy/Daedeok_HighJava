package kr.or.ddit.basic;


import kr.or.ddit.basic.util.JDBCUtil;

import java.sql.*;
import java.util.Scanner;

public class JDBCTest06 {
/*
    Statement 객체를 사용하면 SLQ injection 해킹에 노출될 수 있는 예제

    계좌번호를 검색하는 프로그램
*/
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();
            System.out.println(" --- 계좌번호 검색하기 --- ");
            System.out.println("검색할 계좌번호 입력 > ");

            String bankNo = scan.nextLine();

            // Statement객체 이용하기
//            String sql = " select * from bankinfo " +
//                         " where bank_no = '"+bankNo+"'";

//            st = conn.createStatement();
//            rs = st.executeQuery(sql);


            // PreparedStatement객체 이용하기
            String sql = " select * from bankinfo " +
                    " where bank_no = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, bankNo);
            rs = ps.executeQuery();

            System.out.println(sql);
            System.out.println(" --- 검 색 결 과 ---");
            System.out.println("계좌번호\t 은행이름\t 예금주명\t 개설날짜");
            System.out.println("-------------------------------------------------------");

            while (rs.next()) {
                String no = rs.getString("bank_no");
                String bName = rs.getString("bank_name");
                String uName = rs.getString("bank_user_name");
                String bDate = rs.getString("bank_date");
                System.out.println(no + "\t" + bName  + "\t" + uName + "\t" + bDate);
            }
            System.out.println("-------------------------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(st != null){
                try{
                    st.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if( conn != null){
                try{
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}


