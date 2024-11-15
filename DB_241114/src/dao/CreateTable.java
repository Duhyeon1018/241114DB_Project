package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CreateTable {
    public static ArrayList<String> CreateTables = new ArrayList<>();
    public static ArrayList<String> DeleteTables = new ArrayList<>();

    static {
        // 순서를 보장하여 CreateTables를 채우기
        CreateTables.clear();

        // 1. 사용자 테이블 생성
        CreateTables.add("""
                    CREATE TABLE KUSERTABLE (
                        USERNO NUMBER PRIMARY KEY,
                        USERID VARCHAR2(20) UNIQUE NOT NULL,
                        USERPASSWORD VARCHAR2(20) NOT NULL,
                        USERNAME VARCHAR2(20) NOT NULL
                    )
                """);
        CreateTables.add("""
                    CREATE SEQUENCE KUSERNO_SEQ
                    START WITH 1
                    INCREMENT BY 1
                    NOCACHE
                    NOCYCLE
                """);
        CreateTables.add("""
                    CREATE OR REPLACE TRIGGER KUSERTABLE_TRG
                    BEFORE INSERT ON KUSERTABLE
                    FOR EACH ROW
                    BEGIN
                        :NEW.USERNO := KUSERNO_SEQ.NEXTVAL;
                    END;
                """);

        // 2. 작가, 출판사, 장르 테이블 생성
        CreateTables.add("""
                    CREATE TABLE KAUTHORTABLE(
                        AUTHORNO NUMBER PRIMARY KEY,
                        AUTHORNAME VARCHAR2(20) NOT NULL
                    )
                """);
        CreateTables.add("""
                    CREATE SEQUENCE KAUTHORNO_SEQ
                    START WITH 1
                    INCREMENT BY 1
                    NOCACHE
                    NOCYCLE
                """);
        CreateTables.add("""
                    CREATE OR REPLACE TRIGGER KAUTHORTABLE_TRG
                    BEFORE INSERT ON KAUTHORTABLE
                    FOR EACH ROW
                    BEGIN
                        :NEW.AUTHORNO := KAUTHORNO_SEQ.NEXTVAL;
                    END;
                """);

        CreateTables.add("""
                    CREATE TABLE KPUBLISHERTABLE(
                        PUBLISHERNO NUMBER PRIMARY KEY,
                        PUBLISHERNAME VARCHAR2(20) NOT NULL
                    )
                """);
        CreateTables.add("""
                    CREATE SEQUENCE KPUBLISHER_SEQ
                    START WITH 1
                    INCREMENT BY 1
                    NOCACHE
                    NOCYCLE
                """);
        CreateTables.add("""
                    CREATE OR REPLACE TRIGGER KPUBLISHERTABLE_TRG
                    BEFORE INSERT ON KPUBLISHERTABLE
                    FOR EACH ROW
                    BEGIN
                        :NEW.PUBLISHERNO := KPUBLISHER_SEQ.NEXTVAL;
                    END;
                """);

        CreateTables.add("""
                    CREATE TABLE KGENRETABLE(
                        GENRETABLENO NUMBER PRIMARY KEY,
                        GENRETABLENAME VARCHAR2(20) NOT NULL
                    )
                """);
        CreateTables.add("""
                    CREATE SEQUENCE KGENRETABLE_SEQ
                    START WITH 1
                    INCREMENT BY 1
                    NOCACHE
                    NOCYCLE
                """);
        CreateTables.add("""
                    CREATE OR REPLACE TRIGGER KGENRETABLE_TRG
                    BEFORE INSERT ON KGENRETABLE
                    FOR EACH ROW
                    BEGIN
                        :NEW.GENRETABLENO := KGENRETABLE_SEQ.NEXTVAL;
                    END;
                """);

        // 3. 책 테이블 생성
        CreateTables.add("""
                    CREATE TABLE KBOOKTABLE(
                        BOOKNO NUMBER PRIMARY KEY,
                        BOOKNAME VARCHAR2(20) NOT NULL,
                        AUTHORNO NUMBER,
                        PUBLISHERNO NUMBER,
                        FOREIGN KEY (AUTHORNO) REFERENCES KAUTHORTABLE(AUTHORNO),
                        FOREIGN KEY (PUBLISHERNO) REFERENCES KPUBLISHERTABLE(PUBLISHERNO),
                        COUNT NUMBER
                    )
                """);
        CreateTables.add("""
                    CREATE SEQUENCE KBOOKNO_SEQ
                    START WITH 1
                    INCREMENT BY 1
                    NOCACHE
                    NOCYCLE
                """);
        CreateTables.add("""
                    CREATE OR REPLACE TRIGGER KBOOKTABLE_TRG
                    BEFORE INSERT ON KBOOKTABLE
                    FOR EACH ROW
                    BEGIN
                        :NEW.BOOKNO := KBOOKNO_SEQ.NEXTVAL;
                    END;
                """);

        // 4. 장르-책 매핑 테이블 생성
        CreateTables.add("""
                    CREATE TABLE KGENREBOOKTABLE(
                        GENRETABLENO NUMBER,
                        BOOKNO NUMBER,
                        PRIMARY KEY (GENRETABLENO, BOOKNO),
                        FOREIGN KEY (GENRETABLENO) REFERENCES KGENRETABLE(GENRETABLENO),
                        FOREIGN KEY (BOOKNO) REFERENCES KBOOKTABLE(BOOKNO)
                    )
                """);

        // 5. 랜탈 테이블 생성
        CreateTables.add("""
                    CREATE TABLE KRENTALTABLE (
                        RENTALNO NUMBER PRIMARY KEY,
                        BOOKNO NUMBER,
                        USERNO NUMBER,
                        RENTALSTARTDATE DATE DEFAULT SYSDATE,
                        RENTALENDDATE DATE DEFAULT SYSDATE + 7,
                        FOREIGN KEY (BOOKNO) REFERENCES KBOOKTABLE(BOOKNO),
                        FOREIGN KEY (USERNO) REFERENCES KUSERTABLE(USERNO)
                    )
                """);
        CreateTables.add("""
                    CREATE SEQUENCE KRENTALTABLE_SEQ
                    START WITH 1
                    INCREMENT BY 1
                    NOCACHE
                    NOCYCLE
                """);
        CreateTables.add("""
                    CREATE OR REPLACE TRIGGER KRENTALTABLE_TRG
                    BEFORE INSERT ON KRENTALTABLE
                    FOR EACH ROW
                    BEGIN
                        :NEW.RENTALNO := KRENTALTABLE_SEQ.NEXTVAL;
                    END;
                """);

        // 6. 장바구니 테이블 생성
        CreateTables.add("""
                    CREATE TABLE KSHOPPINGCARTTABLE (
                        SHOPPINGCARTNO NUMBER PRIMARY KEY,
                        BOOKNO NUMBER,
                        USERNO NUMBER,
                        FOREIGN KEY (BOOKNO) REFERENCES KBOOKTABLE(BOOKNO),
                        FOREIGN KEY (USERNO) REFERENCES KUSERTABLE(USERNO)
                    )
                """);
        CreateTables.add("""
                    CREATE SEQUENCE KSHOPPINGCART_SEQ
                    START WITH 1
                    INCREMENT BY 1
                    NOCACHE
                    NOCYCLE
                """);
        CreateTables.add("""
                    CREATE OR REPLACE TRIGGER KSHOPPINGCARTTABLE_TRG
                    BEFORE INSERT ON KSHOPPINGCARTTABLE
                    FOR EACH ROW
                    BEGIN
                        :NEW.SHOPPINGCARTNO := KSHOPPINGCART_SEQ.NEXTVAL;
                    END;
                """);

        // 1. 트리거 삭제
        DeleteTables.add("DROP TRIGGER KUSERTABLE_TRG");
        DeleteTables.add("DROP TRIGGER KAUTHORTABLE_TRG");
        DeleteTables.add("DROP TRIGGER KPUBLISHERTABLE_TRG");
        DeleteTables.add("DROP TRIGGER KGENRETABLE_TRG");
        DeleteTables.add("DROP TRIGGER KBOOKTABLE_TRG");
        DeleteTables.add("DROP TRIGGER KRENTALTABLE_TRG");
        DeleteTables.add("DROP TRIGGER KSHOPPINGCARTTABLE_TRG");

        // 2. 자식 테이블 삭제
        DeleteTables.add("DROP TABLE KGENREBOOKTABLE CASCADE CONSTRAINTS");
        DeleteTables.add("DROP TABLE KSHOPPINGCARTTABLE CASCADE CONSTRAINTS");
        DeleteTables.add("DROP TABLE KRENTALTABLE CASCADE CONSTRAINTS");

        // 3. 부모 테이블 삭제
        DeleteTables.add("DROP TABLE KBOOKTABLE CASCADE CONSTRAINTS");
        DeleteTables.add("DROP TABLE KGENRETABLE CASCADE CONSTRAINTS");
        DeleteTables.add("DROP TABLE KAUTHORTABLE CASCADE CONSTRAINTS");
        DeleteTables.add("DROP TABLE KPUBLISHERTABLE CASCADE CONSTRAINTS");
        DeleteTables.add("DROP TABLE KUSERTABLE CASCADE CONSTRAINTS");

        // 4. 시퀀스 삭제
        DeleteTables.add("DROP SEQUENCE KUSERNO_SEQ");
        DeleteTables.add("DROP SEQUENCE KAUTHORNO_SEQ");
        DeleteTables.add("DROP SEQUENCE KPUBLISHER_SEQ");
        DeleteTables.add("DROP SEQUENCE KGENRETABLE_SEQ");
        DeleteTables.add("DROP SEQUENCE KBOOKNO_SEQ");
        DeleteTables.add("DROP SEQUENCE KRENTALTABLE_SEQ");
        DeleteTables.add("DROP SEQUENCE KSHOPPINGCART_SEQ");
    }

    public static void createTables() {
        for (String sql : CreateTables) {
            CgwDAO.executeUpdateSql(sql);
        }
    }

    public static void deleteTable(){
        for(String sql: DeleteTables){
            CgwDAO.executeUpdateSql(sql);
        }
    }

    public static void main(String[] args) {
        createTables();
//      해당 주석처리한 코드 실행시 모든 테이블, 트리거, 시퀀스 삭제.

//        deleteTable();
    }
}


