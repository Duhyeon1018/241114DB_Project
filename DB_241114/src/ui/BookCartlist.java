package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import dao.KdhBookDAO;

public class BookCartlist extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private KdhBookDAO bookDAO;

    public BookCartlist() {
        bookDAO = new KdhBookDAO(); // KdhBookDAO 인스턴스 생성
        setTitle("나의 장바구니");
        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 테이블 모델 설정
        String[] columnNames = {"책 번호", "책 이름", "작가", "출판사"};
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
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow != -1) {
                    int bookNo = (int) tableModel.getValueAt(selectedRow, 0);
                    int userNo = Search.userNum; // 대여하는 사용자 ID (예: 1로 설정)
                    bookDAO.rentBook(bookNo, userNo); // 대여 메소드 호출
                    JOptionPane.showMessageDialog(null, "책이 대여되었습니다.");
                    loadBooks(); // 대여 후 책 목록 새로 고침
                } else {
                    JOptionPane.showMessageDialog(null, "대여할 책을 선택하세요.");
                }
            }
        });

        // 삭제 버튼 이벤트
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow != -1) {
                    int bookNo = (int) tableModel.getValueAt(selectedRow, 0);
                    bookDAO.deleteBookFromCart(bookNo, Search.userNum); // 삭제 메소드 호출
                    JOptionPane.showMessageDialog(null, "책이 장바구니에서 삭제되었습니다.");
                    loadBooks(); // 삭제 후 책 목록 새로 고침
                } else {
                    JOptionPane.showMessageDialog(null, "삭제할 책을 선택하세요.");
                }
            }
        });

        // 전체 삭제 버튼 이벤트
        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "모든 책을 장바구니에서 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    bookDAO.deleteAllFromCart(Search.userNum); // 전체 삭제 메소드 호출
                    JOptionPane.showMessageDialog(null, "모든 책이 장바구니에서 삭제되었습니다.");
                    loadBooks(); // 전체 삭제 후 책 목록 새로 고침
                }
            }
        });

        // 버튼 패널에 버튼 추가
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(deleteAllButton);

        // 컴포넌트 추가
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        // 화면 중앙에 배치
        setLocationRelativeTo(null);  // 화면 중앙에 창 배치
    }

    private void loadBooks() {
        // 데이터베이스에서 책 목록을 불러와 테이블에 추가
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String userid = "scott";
        String passwd = "tiger";

        String query = """
            SELECT B.BOOKNO, B.BOOKNAME, A.AUTHORNAME AS AUTHOR, P.PUBLISHERNAME AS PUBLISHER
            FROM KSHOPPINGCARTTABLE SC
            JOIN KBOOKTABLE B ON SC.BOOKNO = B.BOOKNO
            JOIN KAUTHORTABLE A ON B.AUTHORNO = A.AUTHORNO
            JOIN KPUBLISHERTABLE P ON B.PUBLISHERNO = P.PUBLISHERNO
            JOIN KUSERTABLE U ON SC.USERNO = U.USERNO
            WHERE U.USERNO =""" + Search.userNum;

        // 테이블 초기화
        tableModel.setRowCount(0); // 기존 데이터 삭제

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("BOOKNO"),
                    rs.getString("BOOKNAME"),
                    rs.getString("AUTHOR"),
                    rs.getString("PUBLISHER")
                };
                tableModel.addRow(row); // 테이블에 데이터 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookCartlist manager = new BookCartlist();
            manager.setVisible(true);
        });
    }
}



