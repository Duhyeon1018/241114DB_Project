package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.KdhBookDTO;

public class KdhBookDAO {
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String userid = "scott";
    private static final String passwd = "tiger";

    public List<KdhBookDTO> getAllBooks() {
        List<KdhBookDTO> books = new ArrayList<>();
        String query = "SELECT BOOKNO, BOOKNAME, GENRETABLENAME AS GENRE, AUTHORNAME AS AUTHOR, PUBLISHERNAME AS PUBLISHER FROM KBOOKTABLE";

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                books.add(new KdhBookDTO(
                        rs.getInt("BOOKNO"),
                        rs.getString("BOOKNAME"),
                        rs.getString("GENRE"),
                        rs.getString("AUTHOR"),
                        rs.getString("PUBLISHER")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void deleteFromCart(int bookNo) {
        String sql = "DELETE FROM KSHOPPINGCARTTABLE WHERE BOOKNO = ?";
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, bookNo);
            pstmt.executeUpdate();
            System.out.println("Book removed from cart: " + bookNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllFromCart() {
        String sql = "DELETE FROM KSHOPPINGCARTTABLE";
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All books removed from cart.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rentBook(int bookNo, int userNo) {
        String sql = "INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (?, ?, SYSDATE, SYSDATE + 7)";
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, bookNo);
            pstmt.setInt(2, userNo);
            pstmt.executeUpdate();
            System.out.println("Book rented successfully: " + bookNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
