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
        String sql = "SELECT KBOOKTABLE.BOOKNO, KBOOKTABLE.BOOKNAME, KGENRETABLE.GENRETABLENAME AS GENRE, "
                   + "KPUBLISHERTABLE.PUBLISHERNAME AS PUBLISHER, KAUTHORTABLE.AUTHORNAME AS AUTHOR "
                   + "FROM KBOOKTABLE "
                   + "JOIN KAUTHORTABLE ON KBOOKTABLE.AUTHORNO = KAUTHORTABLE.AUTHORNO "
                   + "JOIN KPUBLISHERTABLE ON KBOOKTABLE.PUBLISHERNO = KPUBLISHERTABLE.PUBLISHERNO "
                   + "JOIN KGENREBOOKTABLE ON KBOOKTABLE.BOOKNO = KGENREBOOKTABLE.BOOKNO "
                   + "JOIN KGENRETABLE ON KGENREBOOKTABLE.GENRETABLENO = KGENRETABLE.GENRETABLENO";

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

    public List<BookDTO> searchBooksByName(String bookName) {
        List<BookDTO> books = new ArrayList<>();
        String sql = "SELECT KBOOKTABLE.BOOKNO, KBOOKTABLE.BOOKNAME, KGENRETABLE.GENRETABLENAME AS GENRE, "
                   + "KPUBLISHERTABLE.PUBLISHERNAME AS PUBLISHER, KAUTHORTABLE.AUTHORNAME AS AUTHOR "
                   + "FROM KBOOKTABLE "
                   + "JOIN KAUTHORTABLE ON KBOOKTABLE.AUTHORNO = KAUTHORTABLE.AUTHORNO "
                   + "JOIN KPUBLISHERTABLE ON KBOOKTABLE.PUBLISHERNO = KPUBLISHERTABLE.PUBLISHERNO "
                   + "JOIN KGENREBOOKTABLE ON KBOOKTABLE.BOOKNO = KGENREBOOKTABLE.BOOKNO "
                   + "JOIN KGENRETABLE ON KGENREBOOKTABLE.GENRETABLENO = KGENRETABLE.GENRETABLENO "
                   + "WHERE KBOOKTABLE.BOOKNAME LIKE ?";

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + bookName + "%");
            ResultSet rs = pstmt.executeQuery();

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


