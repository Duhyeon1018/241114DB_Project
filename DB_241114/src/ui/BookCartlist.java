package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import dao.KdhBookDAO; // KdhBookDAO를 사용하기 위한 import
import dto.KdhBookDTO; // KdhBookDTO를 사용하기 위한 import


public class BookCartlist extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private KdhBookDAO bookDAO; // KdhBookDAO로 수정

    public BookCartlist() {
        bookDAO = new KdhBookDAO(); // KdhBookDAO 인스턴스 생성
        setTitle("나의 장바구니");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 테이블 모델 설정
        String[] columnNames = {"책 번호", "책 이름", "장르", "작가", "출판사"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // 초기 데이터 로드
        loadBooks();

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("대여");
        JButton deleteButton = new JButton("삭제");
        JButton deleteAllButton = new JButton("전체삭제");

        // 대여 버튼 이벤트
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 대여 버튼 클릭 이벤트
            }
        });

        // 삭제 및 전체 삭제 버튼 이벤트
        // (위에서 작성한 코드와 동일)

        // 버튼 패널에 버튼 추가
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(deleteAllButton);

        // 컴포넌트 추가
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadBooks() {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String userid = "scott";
        String passwd = "tiger";

        String query = """
            SELECT SC.BOOKNO, B.BOOKNAME, G.GENRETABLENAME AS GENRE, A.AUTHORNAME AS AUTHOR, P.PUBLISHERNAME AS PUBLISHER
            FROM KSHOPPINGCARTTABLE SC
            JOIN KBOOKTABLE B ON SC.BOOKNO = B.BOOKNO
            JOIN KGENREBOOKTABLE G ON B.BOOKNO = G.BOOKNO -- KBOOKTABLE과 KGENREBOOKTABLE 조인
            JOIN KAUTHORTABLE A ON B.AUTHORNO = A.AUTHORNO
            JOIN KPUBLISHERTABLE P ON B.PUBLISHERNO = P.PUBLISHERNO
        """;

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("BOOKNO"),
                    rs.getString("BOOKNAME"),
                    rs.getString("GENRE"),
                    rs.getString("AUTHOR"),
                    rs.getString("PUBLISHER")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookCartlist manager = new BookCartlist();
            manager.setVisible(true);
        });
    }
}

