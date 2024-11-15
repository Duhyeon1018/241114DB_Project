package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.BookDAO;
import dto.BookDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookCartlist extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private BookDAO bookDAO;

    public BookCartlist() {
        bookDAO = new BookDAO();
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

        // 추가 버튼 이벤트
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 임시로 테이블에 행 추가
                Object[] newRow = {"새 책 번호", "새 책 이름", "새 장르", "새 작가", "새 출판사"};
                tableModel.addRow(newRow);
                // 데이터베이스에 추가하는 로직 추가 필요
            }
        });

        // 삭제 버튼 이벤트
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow != -1) {
                    int bookNo = (int) tableModel.getValueAt(selectedRow, 0);
                    bookDAO.deleteBook(bookNo); // DB에서 삭제
                    tableModel.removeRow(selectedRow); // 테이블에서 삭제
                } else {
                    JOptionPane.showMessageDialog(null, "삭제할 항목을 선택하세요.");
                }
            }
        });

        // 전체 삭제 버튼 이벤트
        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookDAO.deleteAllBooks(); // DB에서 모든 책 삭제
                tableModel.setRowCount(0); // 테이블에서 모든 행 삭제
            }
        });

        // 버튼 패널에 버튼 추가
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(deleteAllButton);

        // 컴포넌트 추가
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadBooks() {
        List<BookDTO> books = bookDAO.getAllBooks();
        for (BookDTO book : books) {
            Object[] row = {
                book.getBookNo(),
                book.getBookName(),
                book.getGenre(),
                book.getAuthor(),
                book.getPublisher()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookCartlist manager = new BookCartlist();
            manager.setVisible(true);
        });
    }
}

