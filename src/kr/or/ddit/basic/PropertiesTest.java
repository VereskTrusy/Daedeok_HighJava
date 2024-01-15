package kr.or.ddit.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {
    public static void main(String[] args) {
        Properties prop = new Properties();

        File f = new File("./jdcbTest/src/res/kr/or/ddit/config/dbinfo.properties");
        FileInputStream fin = null;

        try {
            fin = new FileInputStream(f);

            prop.load(fin);

            // 출력하기
            System.out.println("driver : " + prop.getProperty("driver"));
            System.out.println("url : " + prop.getProperty("url"));
            System.out.println("user : " + prop.getProperty("user"));
            System.out.println("pass : " + prop.getProperty("pass"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fin != null) { try {fin.close();} catch (IOException e) { e.printStackTrace(); } }
        }
    }
}
