package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.MyRentalDAO;
import dto.MyRentalDTO;
import dao.UserDAO;

public class MyPageUI extends JFrame {
// 화면에 표시할 패널들을 생성
	private JPanel mainPanel;
	private JPanel ViewPanel;
	private JPanel cartPanel;
	private JTable bookTable;
	public DefaultTableModel tableModel;
	private String[] columnNames;
	private MyRentalDAO rentdao;
	private JLabel titleLabel;

	private UserDAO userDAO;
	
	public MyPageUI(int a) {
	    setTitle("마이페이지");
	    setSize(700, 400);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());
	    setLocationRelativeTo(null);

	    userDAO = new UserDAO();
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

	    JButton mainButton = new JButton("메인페이지");
	    buttonPanel.add(mainButton);
	    add(buttonPanel, BorderLayout.NORTH);

	    mainPanel = new JPanel(new BorderLayout());
	    add(mainPanel, BorderLayout.CENTER); // 메인 패널 추가

	    // 버튼 액션
	    mainButton.addActionListener(e -> mainPage());

	    // 조건에 따라 필요한 화면만 표시
	    if (a == 1) {
	        cart();
	    } else if (a == 2) {
	        rentedBooks();
	    }

	    setVisible(true);
	}

	
	private void mainPage() {
	    this.dispose(); // 현재 창 닫기
	    // 사용자 이름 가져오기
	    String userId = null; // 테스트용
	    String userName = userDAO.getUserNameById(userId); // 사용자 이름 가져오기

	    // 새로운 메인 페이지 열기
	    new MainPage("id", userName).setVisible(true);
	}


	public void cart() {
		mainPanel.removeAll();

// 버튼 패널 생성 및 크기 조정
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(0, 50)); // 버튼 패널 높이를 50px로 설정
		JButton addButton = new JButton("대여");
		JButton deleteButton = new JButton("삭제");
		JButton deleteAllButton = new JButton("전체삭제");

		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(deleteAllButton);

// 테이블 생성 및 스크롤 추가
		columnNames = new String[] { "순서", "책 번호", "책 이름", "작가", "출판사" };
		tableModel = new DefaultTableModel(columnNames, 0);
		bookTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(bookTable);

// 장바구니 패널 생성 및 크기 조정
		cartPanel = new JPanel(new BorderLayout());
		cartPanel.setPreferredSize(new Dimension(0, 300)); // cartPanel 높이를 300px로 설정
		cartPanel.add(new JLabel("목록"), BorderLayout.NORTH);
		cartPanel.add(scrollPane, BorderLayout.CENTER);

// mainPanel에 컴포넌트 추가
		mainPanel.add(cartPanel, BorderLayout.CENTER); // 장바구니 패널 추가
		mainPanel.add(buttonPanel, BorderLayout.SOUTH); // 버튼 패널 추가

// UI 갱신
		mainPanel.revalidate();
		mainPanel.repaint();

// ---------------------------------------------------

// 데이터베이스에서 책 목록을 불러와 테이블에 추가
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "scott";
		String passwd = "tiger";

		String query = """
				SELECT ROW_NUMBER() OVER (ORDER BY B.BOOKNO) AS ROW_NUM,
				B.BOOKNO, B.BOOKNAME, A.AUTHORNAME AS AUTHOR, P.PUBLISHERNAME AS PUBLISHER
				FROM KSHOPPINGCARTTABLE SC
				JOIN KBOOKTABLE B ON SC.BOOKNO = B.BOOKNO
				JOIN KAUTHORTABLE A ON B.AUTHORNO = A.AUTHORNO
				JOIN KPUBLISHERTABLE P ON B.PUBLISHERNO = P.PUBLISHERNO
				""";

// 테이블 초기화
		tableModel.setRowCount(0); // 기존 데이터 삭제

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Object[] row = { rs.getInt("ROW_NUM"), // 순서 (ROW_NUMBER)
						rs.getInt("BOOKNO"), // 책 번호
						rs.getString("BOOKNAME"), // 책 이름
						rs.getString("AUTHOR"), // 작가
						rs.getString("PUBLISHER") // 출판사
				};
				tableModel.addRow(row); // 테이블에 데이터 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

// 대여 버튼 클릭 시 동작
		addButton.addActionListener(e -> {
			int selectedRow = bookTable.getSelectedRow();
			if (selectedRow != -1) {
				int bookNo = (int) tableModel.getValueAt(selectedRow, 1);

// 대여 처리 쿼리 (KRENTALTABLE에 삽입)
				String insertRentalQuery = """
						INSERT INTO KRENTALTABLE (BOOKNO, RENTALSTARTDATE)
						VALUES (?, SYSDATE)
						""";
// 장바구니에서 삭제하는 쿼리
				String deleteCartQuery = """
						DELETE FROM KSHOPPINGCARTTABLE
						WHERE BOOKNO = ?
						""";

				try (Connection con = DriverManager.getConnection(url, userid, passwd);
						PreparedStatement rentalStmt = con.prepareStatement(insertRentalQuery);
						PreparedStatement deleteStmt = con.prepareStatement(deleteCartQuery)) {

// 대여 처리
					rentalStmt.setInt(1, bookNo);
					rentalStmt.executeUpdate();

// 장바구니에서 삭제
					deleteStmt.setInt(1, bookNo);
					deleteStmt.executeUpdate();

// 테이블에서 해당 행 삭제
					tableModel.removeRow(selectedRow);

// 대여 후 사용자에게 알림
					JOptionPane.showMessageDialog(null, "책이 대여되었습니다.");

// 장바구니 테이블을 갱신하여 순서를 다시 정렬
					refreshCartTable(); // 순서 재정렬

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "책을 선택해주세요.");
			}
		});

// 삭제 버튼 클릭 시 동작 (선택된 책을 장바구니에서 삭제)
		deleteButton.addActionListener(e -> {
			int selectedRow = bookTable.getSelectedRow();
			if (selectedRow != -1) {
				int bookNo = (int) tableModel.getValueAt(selectedRow, 1);

// 장바구니에서 해당 책 삭제하는 쿼리
				String deleteQuery = """
						DELETE FROM KSHOPPINGCARTTABLE
						WHERE BOOKNO = ?
						""";

				try (Connection con = DriverManager.getConnection(url, userid, passwd);
						PreparedStatement deleteStmt = con.prepareStatement(deleteQuery)) {

// 삭제 처리
					deleteStmt.setInt(1, bookNo);
					deleteStmt.executeUpdate();

// 테이블에서 해당 행 삭제
					tableModel.removeRow(selectedRow);

// 장바구니 테이블을 갱신하여 순서를 다시 정렬
					refreshCartTable(); // 순서 재정렬

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "책을 선택해주세요.");
			}
		});

// 전체삭제 버튼 클릭 시 장바구니 모든 책 삭제
		deleteAllButton.addActionListener(e -> {
			String deleteAllQuery = "DELETE FROM KSHOPPINGCARTTABLE";
			try (Connection con = DriverManager.getConnection(url, userid, passwd);
					Statement stmt = con.createStatement()) {

// 모든 책 삭제
				stmt.executeUpdate(deleteAllQuery);

// 테이블에서 모든 행 삭제
				tableModel.setRowCount(0);

// 장바구니 테이블을 갱신하여 순서를 다시 정렬
				refreshCartTable(); // 순서 재정렬

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
	}

	private void refreshCartTable() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "scott";
		String passwd = "tiger";

		String query = """
				SELECT ROW_NUMBER() OVER (ORDER BY B.BOOKNO) AS ROW_NUM,
				B.BOOKNO, B.BOOKNAME, A.AUTHORNAME AS AUTHOR, P.PUBLISHERNAME AS PUBLISHER
				FROM KSHOPPINGCARTTABLE SC
				JOIN KBOOKTABLE B ON SC.BOOKNO = B.BOOKNO
				JOIN KAUTHORTABLE A ON B.AUTHORNO = A.AUTHORNO
				JOIN KPUBLISHERTABLE P ON B.PUBLISHERNO = P.PUBLISHERNO
				""";

// 테이블 초기화
		tableModel.setRowCount(0); // 기존 데이터 삭제

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Object[] row = { rs.getInt("ROW_NUM"), // 순서 (ROW_NUMBER)
						rs.getInt("BOOKNO"), // 책 번호
						rs.getString("BOOKNAME"), // 책 이름
						rs.getString("AUTHOR"), // 작가
						rs.getString("PUBLISHER") // 출판사
				};
				tableModel.addRow(row); // 테이블에 데이터 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

// UI 갱신
		mainPanel.revalidate();
		mainPanel.repaint();
	}



	private void rentedBooks() {

		// 새로운 테이블 및 스크롤 패널 생성
		columnNames = new String[] { "대여번호", "제목", "대여시작일", "대여종료일", "상태" };
		tableModel = new DefaultTableModel(columnNames, 0);
		
		JTable boardTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(boardTable);

		// ViewPanel을 새로 구성하여 mainPanel에 추가
		ViewPanel = new JPanel();
		ViewPanel.setLayout(new BorderLayout());
		
		ViewPanel.add(scrollPane, BorderLayout.CENTER);

		// mainPanel에 ViewPanel을 추가 (왼쪽에 위치하도록 설정)
		mainPanel.add(ViewPanel);

		// ---------------------------------------------------

		// DAO 객체 초기화
		if (rentdao == null) {
			rentdao = new MyRentalDAO();
		}

		// 이전 데이터 제거
		tableModel.setRowCount(0);

		// 대여 기록 조회
		ArrayList<MyRentalDTO> resultList = rentdao.getRentalAllBooks();

		if (resultList.isEmpty()) {
			System.out.println("No rental records found.");
		}

		// 현재 날짜
		LocalDate currentDate = LocalDate.now();

		// 날짜 형식 지정
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (MyRentalDTO rentalDTO : resultList) {
			LocalDate rentalEndDate = null;
			LocalDate rentalStartDate = null;

			try {
				// 대여 종료 날짜 처리 (시간 부분 제거)
				String rentalEndDateStr = rentalDTO.getReturnDate().split(" ")[0];
				rentalEndDate = LocalDate.parse(rentalEndDateStr);

				// 대여 시작 날짜 처리 (시간 부분 제거)
				String rentalStartDateStr = rentalDTO.getRentalDate().split(" ")[0];
				rentalStartDate = LocalDate.parse(rentalStartDateStr);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format: " + e.getMessage());
			}

			// 상태 계산
			String status = (rentalEndDate != null && rentalEndDate.isBefore(currentDate)) ? "반납완료" : "대여중";

			// 날짜 출력 형식 변경
			String formattedRentalStartDate = rentalStartDate != null ? rentalStartDate.format(formatter) : "N/A";
			String formattedReturnDate = rentalEndDate != null ? rentalEndDate.format(formatter) : "N/A";

			Object[] rowData = { rentalDTO.getRentalId(), rentalDTO.getBookName(), formattedRentalStartDate,
					formattedReturnDate, status };

			tableModel.addRow(rowData);
		}

	}

	private void logout() {
// 로그아웃 처리 로직
		JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
		dispose(); // 현재 창 닫기 (로그아웃 후 로그인 화면으로 이동)
		new LoginUI().setVisible(true);

	}

	public static void main(String[] args) {
		new MyPageUI(2);
	}
}