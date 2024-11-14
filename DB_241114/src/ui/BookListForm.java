package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dao.BookDAO;
import dto.BookDTO;

public class BookListForm extends JFrame {
    private JTable table; // 인스턴스 변수로 선언
    private BookDAO bookDAO; // BookDAO 인스턴스
    private JTextField searchField; // 검색 필드

    public BookListForm() {
        setTitle("도서목록");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 창을 띄우기
        
        // 패널 생성 및 컴포넌트 추가
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // 상단에 검색 및 필터 버튼들 추가
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        
        searchField = new JTextField(15); // 검색 필드
        topPanel.add(searchField);
        
        JButton searchButton = new JButton("검색");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBookDataByName(searchField.getText());
            }
        });
        topPanel.add(searchButton);
        
        topPanel.add(new JButton("장르별")); // 장르별 필터 버튼
        topPanel.add(new JButton("출판사별")); // 출판사별 필터 버튼
        topPanel.add(new JButton("작가별")); // 작가별 필터 버튼
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        // 중앙에 테이블 추가
        String[] columnNames = {"번호", "책이름", "장르", "출판사", "작가"};
        Object[][] data = {}; // 초기 데이터는 빈 배열
        
        table = new JTable(data, columnNames); // 인스턴스 변수 사용
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // 추가 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("대여"));
        buttonPanel.add(new JButton("대여하기"));
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
        
        bookDAO = new BookDAO(); // BookDAO 초기화
        loadBookData(); // 도서 데이터 로드
    }

    private void loadBookData() {
        List<BookDTO> books = bookDAO.getAllBooks();

        // 데이터 배열로 변환
        Object[][] data = new Object[books.size()][5];
        for (int i = 0; i < books.size(); i++) {
            BookDTO book = books.get(i);
            data[i][0] = book.getBookNo();
            data[i][1] = book.getBookName();
            data[i][2] = book.getGenre();
            data[i][3] = book.getPublisher();
            data[i][4] = book.getAuthor();
        }

        // 테이블 모델 업데이트
        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {"번호", "책이름", "장르", "출판사", "작가"}));
    }

    private void loadBookDataByName(String bookName) {
        List<BookDTO> books = bookDAO.searchBooksByName(bookName);

        // 데이터 배열로 변환
        Object[][] data = new Object[books.size()][5];
        for (int i = 0; i < books.size(); i++) {
            BookDTO book = books.get(i);
            data[i][0] = book.getBookNo();
            data[i][1] = book.getBookName();
            data[i][2] = book.getGenre();
            data[i][3] = book.getPublisher();
            data[i][4] = book.getAuthor();
        }

        // 테이블 모델 업데이트
        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {"번호", "책이름", "장르", "출판사", "작가"}));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookListForm bookListForm = new BookListForm();
            bookListForm.setVisible(true);
        });
    }
}

