package dao;


import java.sql.*;
import java.util.*;

import dto.MyRentalDTO;

public class MyRentalDAO {
    private Connection conn;

    public MyRentalDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // 전체 책 목록 조회
    public ArrayList<MyRentalDTO> getRentalAllBooks() {
        ArrayList<MyRentalDTO> books = new ArrayList<>();
        String sql = "SELECT R.RENTALNO, B.BOOKNAME, R.USERNO, R.RENTALSTARTDATE, R.RENTALENDDATE\r\n"
        		+ "FROM KRENTALTABLE R JOIN KBOOKTABLE B \r\n"
        		+ "ON R.BOOKNO = B.BOOKNO";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	MyRentalDTO book = new MyRentalDTO();
                book.setRentalId(rs.getInt("RENTALNO"));  // 컬럼 이름을 실제 테이블에 맞게 수정
                book.setBookName(rs.getString("BOOKNAME"));      // 컬럼 이름을 실제 테이블에 맞게 수정
                book.setUserId(rs.getInt("USERNO"));      // 컬럼 이름을 실제 테이블에 맞게 수정
                book.setRentalDate(rs.getString("RENTALSTARTDATE")); // 대여시작일 컬럼 이름 수정
                book.setReturnDate(rs.getString("RENTALENDDATE"));  // 대여종료일 컬럼 이름 수정
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }








}
