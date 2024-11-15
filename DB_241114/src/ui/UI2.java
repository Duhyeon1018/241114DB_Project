package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UI2 extends JFrame {
    private JTextField wordField;
    private JPanel ViewPanel;
    private DefaultTableModel tableModel;

    private String[] columnNames;
    JTable boardTable ;
    JScrollPane scrollPane ;

    public UI2() {
        setTitle("전자 도서관");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        String[] genres = {"전체", "장르", "출판사", "작가"};
        JComboBox<String> genreComboBox = new JComboBox<>(genres);
        inputPanel.add(new JLabel("검색 옵션:"));
        inputPanel.add(genreComboBox);

        wordField = new JTextField(36);
        inputPanel.add(wordField);

        JButton addButton = new JButton("검색");
        inputPanel.add(addButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        
        JButton allShowButton = new JButton("전체보기");
        JButton topButton = new JButton("TOP10");
        JButton myPageButton = new JButton("마이페이지");

        buttonPanel.add(allShowButton);
        buttonPanel.add(topButton);
        buttonPanel.add(myPageButton);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.add(inputPanel, BorderLayout.NORTH);
        northPanel.add(buttonPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);

        ViewPanel = new JPanel();
        ViewPanel.setLayout(new BorderLayout());

        columnNames = new String[] { "NO", "제목", "작가", "장르" };
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable boardTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(boardTable);

        ViewPanel.add(new JLabel("목록"), BorderLayout.NORTH);
        ViewPanel.add(scrollPane, BorderLayout.CENTER);

        add(ViewPanel, BorderLayout.CENTER);

        allShowButton.addActionListener(e -> allShow());  // 전체 책 보기
        topButton.addActionListener(e -> top());         // TOP10
        myPageButton.addActionListener(e -> myPage());   // 마이페이지

        setVisible(true);
    }

    private void allShow() {
        // 전체 책 보기 로직 구현
    }

    private void genre() {
        // 장르별 책 보기 로직 구현
    }

    private void Company() {
        // 출판사별 책 보기 로직 구현
    }

    private void writer() {
        // 작가별 책 보기 로직 구현
    }

    private void top() {
        // TOP10 책 보기 로직 구현
    }

    private void myPage() {
        // 마이페이지 보기 로직 구현
        this.dispose();
    	new MyPageUI().setVisible(true);
    }

    public static void main(String[] args) {
        new UI2();  // UI 실행
    }
}

//수정완