package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dto.BookDTO;

public class BookDAO {
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String userid = "scott";
    private static final String passwd = "tiger";

    public List<BookDTO> getAllBooks() {
        List<BookDTO> books = new ArrayList<>();
        String sql = "SELECT BOOKNO, BOOKNAME, GENRE, PUBLISHER, AUTHOR FROM KBOOKTABLE"; // 예시 쿼리

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BookDTO book = new BookDTO(
                    rs.getInt("BOOKNO"),
                    rs.getString("BOOKNAME"),
                    rs.getString("GENRE"),
                    rs.getString("PUBLISHER"),
                    rs.getString("AUTHOR")
                );
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
