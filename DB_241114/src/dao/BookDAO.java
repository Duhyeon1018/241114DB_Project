package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.BookDTO;

public class BookDAO {
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String userid = "scott";
    private static final String passwd = "tiger";

    public List<BookDTO> getAllBooks() {
        List<BookDTO> books = new ArrayList<>();
        String query = "SELECT BOOKNO, BOOKNAME, GENRE, AUTHORNO, PUBLISHERNO FROM KBOOKTABLE";

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                books.add(new BookDTO(
                        rs.getInt("BOOKNO"),
                        rs.getString("BOOKNAME"),
                        rs.getString("GENRE"),
                        rs.getString("AUTHORNO"),
                        rs.getString("PUBLISHERNO")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void deleteBook(int bookNo) {
        String sql = "DELETE FROM KBOOKTABLE WHERE BOOKNO = ?";
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, bookNo);
            pstmt.executeUpdate();
            System.out.println("Book deleted successfully: " + bookNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllBooks() {
        String sql = "DELETE FROM KBOOKTABLE";
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All books deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




