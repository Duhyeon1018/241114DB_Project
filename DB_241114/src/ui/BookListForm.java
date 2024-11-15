package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookListForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public BookListForm() {
        setTitle("도서목록");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 새 창만 닫기
        setLocationRelativeTo(null); // 화면 중앙에 창을 띄우기

        // 전체 패널 설정
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 상단 패널 설정: 검색 및 필터 버튼
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("검색");
        JButton allButton = new JButton("전체목록");
        JButton genreButton = new JButton("장르별");
        JButton publisherButton = new JButton("출판사별");
        JButton authorButton = new JButton("작가별");
        JButton bestSellerButton = new JButton("베스트셀러");

        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(allButton);
        topPanel.add(genreButton);
        topPanel.add(publisherButton);
        topPanel.add(authorButton);
        topPanel.add(bestSellerButton);

        panel.add(topPanel, BorderLayout.NORTH);

        // 중앙 테이블 설정
        String[] columnNames = {"번호", "책이름", "장르", "출판사", "작가", "장바구니"};
        Object[][] data = {
            
        };
        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return column == 5; // 마지막 열만 편집 가능 (장바구니 버튼)
            }
        };

        table.getColumn("장바구니").setCellRenderer(new ButtonRenderer());
        table.getColumn("장바구니").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // 각 버튼의 액션 리스너 추가
        searchButton.addActionListener(e -> searchBooks(searchField.getText()));
        allButton.addActionListener(e -> showAllBooks());
        genreButton.addActionListener(e -> filterByGenre());
        publisherButton.addActionListener(e -> filterByPublisher());
        authorButton.addActionListener(e -> filterByAuthor());
        bestSellerButton.addActionListener(e -> showBestSellers());
    }

    // 검색 기능 구현
    private void searchBooks(String query) {
        JOptionPane.showMessageDialog(this, "검색 기능 호출됨: " + query);
        // 검색 기능 코드 추가 필요
    }

    // 전체목록 보기 기능
    private void showAllBooks() {
        JOptionPane.showMessageDialog(this, "전체목록 보기 호출됨");
        // 전체목록 보기 코드 추가 필요
    }

    // 장르별 필터 기능
    private void filterByGenre() {
        JOptionPane.showMessageDialog(this, "장르별 필터 기능 호출됨");
        // 장르별 필터 코드 추가 필요
    }

    // 출판사별 필터 기능
    private void filterByPublisher() {
        JOptionPane.showMessageDialog(this, "출판사별 필터 기능 호출됨");
        // 출판사별 필터 코드 추가 필요
    }

    // 작가별 필터 기능
    private void filterByAuthor() {
        JOptionPane.showMessageDialog(this, "작가별 필터 기능 호출됨");
        // 작가별 필터 코드 추가 필요
    }

    // 베스트셀러 보기 기능
    private void showBestSellers() {
        JOptionPane.showMessageDialog(this, "베스트셀러 보기 호출됨");
        // 베스트셀러 보기 코드 추가 필요
    }

    // 테이블 셀에 버튼을 렌더링하기 위한 클래스
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // 테이블 셀에 버튼을 에디터로 사용하기 위한 클래스
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                JOptionPane.showMessageDialog(button, label + " 버튼이 눌렸습니다.");
                // "담기"나 "대여불가" 버튼을 눌렀을 때의 동작 추가 가능
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookListForm bookListForm = new BookListForm();
            bookListForm.setVisible(true);
        });
    }
}


