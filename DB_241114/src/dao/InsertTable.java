package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class InsertTable {
    public static ArrayList<String> InsertData = new ArrayList<>();

    static {
        // 사용자 테이블 데이터
        InsertData.add("INSERT INTO KUSERTABLE (USERID, USERPASSWORD, USERNAME) VALUES ('user1', 'password1', 'John Doe')");
        InsertData.add("INSERT INTO KUSERTABLE (USERID, USERPASSWORD, USERNAME) VALUES ('user2', 'password2', 'Jane Smith')");
        InsertData.add("INSERT INTO KUSERTABLE (USERID, USERPASSWORD, USERNAME) VALUES ('user3', 'password3', 'Alice Johnson')");
        InsertData.add("INSERT INTO KUSERTABLE (USERID, USERPASSWORD, USERNAME) VALUES ('user4', 'password4', 'Bob Brown')");
        InsertData.add("INSERT INTO KUSERTABLE (USERID, USERPASSWORD, USERNAME) VALUES ('user5', 'password5', 'Charlie Davis')");

        // 작가 테이블 데이터
        InsertData.add("INSERT INTO KAUTHORTABLE (AUTHORNAME) VALUES ('Author A')");
        InsertData.add("INSERT INTO KAUTHORTABLE (AUTHORNAME) VALUES ('Author B')");
        InsertData.add("INSERT INTO KAUTHORTABLE (AUTHORNAME) VALUES ('Author C')");
        InsertData.add("INSERT INTO KAUTHORTABLE (AUTHORNAME) VALUES ('Author D')");
        InsertData.add("INSERT INTO KAUTHORTABLE (AUTHORNAME) VALUES ('Author E')");

        // 출판사 테이블 데이터
        InsertData.add("INSERT INTO KPUBLISHERTABLE (PUBLISHERNAME) VALUES ('Publisher X')");
        InsertData.add("INSERT INTO KPUBLISHERTABLE (PUBLISHERNAME) VALUES ('Publisher Y')");
        InsertData.add("INSERT INTO KPUBLISHERTABLE (PUBLISHERNAME) VALUES ('Publisher Z')");
        InsertData.add("INSERT INTO KPUBLISHERTABLE (PUBLISHERNAME) VALUES ('Publisher W')");
        InsertData.add("INSERT INTO KPUBLISHERTABLE (PUBLISHERNAME) VALUES ('Publisher V')");

        // 장르 테이블 데이터
        InsertData.add("INSERT INTO KGENRETABLE (GENRETABLENAME) VALUES ('Fiction')");
        InsertData.add("INSERT INTO KGENRETABLE (GENRETABLENAME) VALUES ('Non-Fiction')");
        InsertData.add("INSERT INTO KGENRETABLE (GENRETABLENAME) VALUES ('Mystery')");
        InsertData.add("INSERT INTO KGENRETABLE (GENRETABLENAME) VALUES ('Science Fiction')");
        InsertData.add("INSERT INTO KGENRETABLE (GENRETABLENAME) VALUES ('Fantasy')");

        // 책 테이블 데이터
        InsertData.add("INSERT INTO KBOOKTABLE (BOOKNAME, AUTHORNO, PUBLISHERNO, COUNT) VALUES ('Book 1', 1, 1, 10)");
        InsertData.add("INSERT INTO KBOOKTABLE (BOOKNAME, AUTHORNO, PUBLISHERNO, COUNT) VALUES ('Book 2', 2, 2, 5)");
        InsertData.add("INSERT INTO KBOOKTABLE (BOOKNAME, AUTHORNO, PUBLISHERNO, COUNT) VALUES ('Book 3', 3, 3, 8)");
        InsertData.add("INSERT INTO KBOOKTABLE (BOOKNAME, AUTHORNO, PUBLISHERNO, COUNT) VALUES ('Book 4', 4, 4, 12)");
        InsertData.add("INSERT INTO KBOOKTABLE (BOOKNAME, AUTHORNO, PUBLISHERNO, COUNT) VALUES ('Book 5', 5, 5, 7)");

        // 장르-책 매핑 테이블 데이터
        InsertData.add("INSERT INTO KGENREBOOKTABLE (GENRETABLENO, BOOKNO) VALUES (1, 1)");
        InsertData.add("INSERT INTO KGENREBOOKTABLE (GENRETABLENO, BOOKNO) VALUES (2, 2)");
        InsertData.add("INSERT INTO KGENREBOOKTABLE (GENRETABLENO, BOOKNO) VALUES (3, 3)");
        InsertData.add("INSERT INTO KGENREBOOKTABLE (GENRETABLENO, BOOKNO) VALUES (4, 4)");
        InsertData.add("INSERT INTO KGENREBOOKTABLE (GENRETABLENO, BOOKNO) VALUES (5, 5)");

        // 랜탈 테이블 데이터
        InsertData.add("INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (1, 1, SYSDATE, SYSDATE + 7)");
        InsertData.add("INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (2, 2, SYSDATE, SYSDATE + 7)");
        InsertData.add("INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (3, 3, SYSDATE, SYSDATE + 7)");
        InsertData.add("INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (4, 4, SYSDATE, SYSDATE + 7)");
        InsertData.add("INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (5, 5, SYSDATE, SYSDATE + 7)");

        // 장바구니 테이블 데이터
        InsertData.add("INSERT INTO KSHOPPINGCARTTABLE (BOOKNO, USERNO) VALUES (1, 1)");
        InsertData.add("INSERT INTO KSHOPPINGCARTTABLE (BOOKNO, USERNO) VALUES (2, 1)");
        InsertData.add("INSERT INTO KSHOPPINGCARTTABLE (BOOKNO, USERNO) VALUES (3, 2)");
        InsertData.add("INSERT INTO KSHOPPINGCARTTABLE (BOOKNO, USERNO) VALUES (4, 3)");
        InsertData.add("INSERT INTO KSHOPPINGCARTTABLE (BOOKNO, USERNO) VALUES (5, 4)");
    }
    public static void inputData() {
        for (String sql: InsertData) {
            CgwDAO.executeUpdateSql(sql);
        }
    }
    public static void main(String[] args) {
        inputData();
    }
}
