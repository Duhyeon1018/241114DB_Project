package ui;

import javax.swing.*;
import java.awt.*;

public class BookListForm extends JFrame {
    public BookListForm() {
        setTitle("도서목록");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 새 창만 닫기
        setLocationRelativeTo(null); // 화면 중앙에 창을 띄우기
        
        // 패널 생성 및 컴포넌트 추가
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // 상단에 검색 및 필터 버튼들 추가
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JTextField(15)); // 검색 필드
        topPanel.add(new JButton("검색"));
        topPanel.add(new JButton("장르별"));
        topPanel.add(new JButton("출판사별"));
        topPanel.add(new JButton("작가별"));
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        // 중앙에 테이블 추가
        String[] columnNames = {"번호", "책이름", "장르", "출판사", "작가"};
        Object[][] data = {}; // 데이터베이스 연결 시 실제 데이터를 여기에 연결
        
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // 추가 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("대여"));
        buttonPanel.add(new JButton("대여하기"));
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookListForm bookListForm = new BookListForm();
            bookListForm.setVisible(true);
        });
    }
}

