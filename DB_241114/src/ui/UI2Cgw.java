package ui;

import dao.CgwDAO;
import dao.CgwSQL;
import dao.KdhBookDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import dao.UserDAO;

public class UI2Cgw extends JFrame {
	private JPanel ViewPanel;
	public DefaultTableModel tableModel;
	private String[] columnNames;
	private JComboBox<String> genreComboBox;
	private String userId;
	private String userName;
	private JTable boardTable;
	private KdhBookDAO bookDAO = new KdhBookDAO();

	private UserDAO userDAO;
	
	public String getComboBoxString() {
		if (genreComboBox.getSelectedItem() != null) {
			return genreComboBox.getSelectedItem().toString();
		} else
			return "";
	}

	public UI2Cgw(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
		setTitle("전자 도서관");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

        userDAO = new UserDAO();
        
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());

		columnNames = new String[] { "NO", "제목", "작가", "출판사", "장르", "대출가능여부" };
		tableModel = new DefaultTableModel(columnNames, 0);
		boardTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(boardTable);

		String[] genres = { "책이름", "장르", "출판사", "작가" };
		genreComboBox = new JComboBox<>(genres);
		inputPanel.add(new JLabel("검색 옵션:"));
		inputPanel.add(genreComboBox);
		Search serachPanel = new Search(this);

		inputPanel.add(serachPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton allShowButton = new JButton("전체보기");
		JButton topButton = new JButton("TOP10");
		JButton myPageButton = new JButton("메인페이지");

		buttonPanel.add(allShowButton);
		buttonPanel.add(topButton);
		buttonPanel.add(myPageButton);

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		northPanel.add(inputPanel, BorderLayout.NORTH);
		northPanel.add(buttonPanel, BorderLayout.CENTER);

		add(northPanel, BorderLayout.NORTH);
// 화면 중앙에 배치
		setLocationRelativeTo(null); // 화면 중앙에 창 배치

		ViewPanel = new JPanel();
		ViewPanel.setLayout(new BorderLayout());

		ViewPanel.add(new JLabel("목록"), BorderLayout.NORTH);
		ViewPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel takeButtonPanel = new JPanel();
		takeButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton rentalButton = new JButton("대여");
		JButton shoppingCartButton = new JButton("장바구니");
		takeButtonPanel.add(rentalButton);
		takeButtonPanel.add(shoppingCartButton);

		add(ViewPanel, BorderLayout.CENTER);
		add(takeButtonPanel, BorderLayout.SOUTH);

		allShowButton.addActionListener(e -> allShow()); // 전체 책 보기
		topButton.addActionListener(e -> top()); // TOP10
		myPageButton.addActionListener(e -> mainPage()); // 메인페이
		rentalButton.addActionListener(e -> rentalClick());
		shoppingCartButton.addActionListener(e -> shoppingCartClick());

		setVisible(true);
	}

	private void allShow() {
		ArrayList<String> inputData = new ArrayList<>(List.of(Search.userNum + ""));
		LinkedHashMap<String, ArrayList<String>> dataMaps = CgwDAO.getData(inputData, CgwSQL.allShowSqlType,
				CgwSQL.allShowSql);
		String[][] dataArrays = Search.convertMapTo2DArray(dataMaps);
		tableModel.setDataVector(dataArrays, columnNames);
	}

	private void top() {
		ArrayList<String> inputData = new ArrayList<>(List.of(Search.userNum + ""));
		LinkedHashMap<String, ArrayList<String>> dataMaps = CgwDAO.getData(inputData, CgwSQL.top10ShowSqlType,
				CgwSQL.top10ShowSql);
		String[][] dataArrays = Search.convertMapTo2DArray(dataMaps);
		tableModel.setDataVector(dataArrays, columnNames);
	}

	private void mainPage() {
	    this.dispose(); // 현재 창 닫기
	    // 사용자 이름 가져오기
	    String userId = null; // 테스트용
	    String userName = userDAO.getUserNameById(userId); // 사용자 이름 가져오기

	    // 새로운 메인 페이지 열기
	    new MainPage("id", userName).setVisible(true);
	}


	public void rentalClick() {
		int selectedRow = boardTable.getSelectedRow();
		if (selectedRow != -1) {
			if (tableModel.getValueAt(selectedRow, 5).equals("대여가능")) {
				Object value = tableModel.getValueAt(selectedRow, 0);
				int bookNo = Integer.parseInt((String) value);
				bookDAO.rentBook(bookNo, Search.userNum); // 대여 메소드 호출
				JOptionPane.showMessageDialog(null, "책이 대여되었습니다.");
				tableModel.setValueAt("대여불가", selectedRow, 5);
			} else
				JOptionPane.showMessageDialog(null, "이미 대여된 책입니다.");
		} else
			JOptionPane.showMessageDialog(null, "책을 선택해주세요.");
	}

	public void shoppingCartClick() {
		int selectedRow = boardTable.getSelectedRow();
		if (selectedRow != -1) {
			if (tableModel.getValueAt(selectedRow, 5).equals("대여가능")) {
				Object value = tableModel.getValueAt(selectedRow, 0);
				int bookNo = Integer.parseInt((String) value);
				ArrayList<String> inputData = new ArrayList<>(
						Arrays.asList(bookNo + "", Search.userNum + "", bookNo + "", Search.userNum + ""));
				if (CgwDAO.executeUpdateSql(CgwSQL.shoppingCartSql, inputData, CgwSQL.shoppingCartSqlType)) {
					JOptionPane.showMessageDialog(null, "장바구니에 책을 추가하였습니다.");
				} else
					JOptionPane.showMessageDialog(null, "장바구니에 책이 존재합니다.");
			} else
				JOptionPane.showMessageDialog(null, "이미 대여된 책입니다.");
		} else
			JOptionPane.showMessageDialog(null, "책을 선택해주세요.");
	}

	public static void main(String[] args) {
		new UI2Cgw("id", "pas"); // UI 실행
	}
}