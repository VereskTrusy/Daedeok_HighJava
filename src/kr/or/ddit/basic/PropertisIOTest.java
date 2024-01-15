package kr.or.ddit.basic;

import java.io.*;
import java.util.Properties;


/*
    Collection 의 Properties 객체를 활용
    res.kr.or.ddit.config 하위 memo.properties 파일 쓰고 읽기 예제
 */
public class PropertisIOTest {

    public static void main(String[] args) {
        // Properties 객체를 이용한 데이ㅓ 입력출력 예제
        Properties prop = new Properties();

        // Properties 객체로 데이터를 저장하고 읽어올 파일 정보가 저장된 File객체 생성
        File file = new File("./jdcbTest/src/res/kr/or/ddit/config/memo.properties");
        //File file = new File(".");
        System.out.println(file.getAbsolutePath()); // 현재폴더 기준 알 수 있음
        FileOutputStream fout = null;

        try {
            // 저장할 내용을 Properties 객체에 추가하기
            prop.setProperty("name", "홍길동");
            prop.setProperty("age", String.valueOf(25));
            prop.setProperty("addr", "대전시 중구 오류동");
            prop.setProperty("tel", "010-1234-5678");

            // Properties 객체를 저장할 출력용 스트림 객체 생성
            fout = new FileOutputStream(file);

            // 저장하기
            prop.store(fout, "이것은 주석 내용 입니다.");

            System.out.println("출력 작업 완료");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if( fout != null) { try { fout.close(); } catch (IOException e) { e.printStackTrace(); }
            }
        }

        System.out.println("-------------------------------------------------------------------------");

        // 읽어온 정보를 저장 할 Properties 객체 생성
        Properties prop2 = new Properties();

        // 읽기용 스트림 객체 생성
        FileInputStream fin = null;

        try {
            // 스트림 객체 생성
            fin = new FileInputStream(file);

            // 입력용 스트림 이용하여 파일 내용을 읽어와 Properties 객체에 저장하기
            // 파일 내용을 읽어와 key값과 value값을 분류한 후 Properties 객체에 추가한다.
            prop2.load(fin);

            // 읽어온 정보를 출력해 보기
            System.out.println("name : " + prop2.getProperty("name"));
            System.out.println("age : " + prop2.getProperty("age"));
            System.out.println("addr : " + prop2.getProperty("addr"));
            System.out.println("tel : " + prop2.getProperty("tel"));

            System.out.println("읽기 작업 완료!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fin != null) { try {fin.close();} catch (IOException e) { e.printStackTrace(); } }
        }
    }
}
