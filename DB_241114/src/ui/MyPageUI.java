package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class MyPageUI extends JFrame {
	// 화면에 표시할 패널들을 생성
	private JPanel mainPanel;
	private JPanel ViewPanel;
	private JPanel cartPanel;
	private JTable bookTable;
	public DefaultTableModel tableModel;
	private String[] columnNames;
	private MyRentalDAO rentdao;
	private JPanel mmPanel;

	public MyPageUI() {

		setTitle("마이페이지");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		// 버튼을 담을 패널 (오른쪽 세로 정렬, 고정 크기)
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // 세로 정렬
		buttonPanel.setPreferredSize(new Dimension(150, 0)); // 패널의 고정 너비 설정
		// buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // 오른쪽에
		// 20px 여백 추가

		// 오른쪽 여백을 20px로 추가하고, 패널이 오른쪽에 배치되도록 설정
		add(buttonPanel, BorderLayout.EAST); // 버튼 패널을 오른쪽에 배치
		
		// 각 버튼 생성 및 크기 고정
		Dimension buttonSize = new Dimension(100, 50);
		JButton libraryButton = new JButton("도서관");
		JButton cartButton = new JButton("장바구니");
		JButton infoButton = new JButton("내정보");
		JButton rentedBooksButton = new JButton("대여중인책");
		JButton logoutButton = new JButton("로그아웃");

		// 버튼 고정 크기 설정
		libraryButton.setMaximumSize(buttonSize);
		cartButton.setMaximumSize(buttonSize);
		infoButton.setMaximumSize(buttonSize);
		rentedBooksButton.setMaximumSize(buttonSize);
		logoutButton.setMaximumSize(buttonSize);

		// 버튼들을 패널에 추가하고 고정된 위치에 유지
		// buttonPanel.add(Box.createVerticalGlue()); // 상단 여백을 위한 글루
		buttonPanel.add(Box.createVerticalStrut(30));
		buttonPanel.add(libraryButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(cartButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(infoButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(rentedBooksButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(logoutButton);
		buttonPanel.add(Box.createVerticalGlue());

		mainPanel = new JPanel();

		// 전체 레이아웃 설정
		setLayout(new BorderLayout());
//        add()
		add(buttonPanel, BorderLayout.EAST);
		add(mainPanel, BorderLayout.CENTER);

		// 초기 화면 설정
		// cardLayout.show(mainPanel, "Library");

		libraryButton.addActionListener(e -> library()); // 도서관이동
		cartButton.addActionListener(e -> cart()); // 장바구니
		infoButton.addActionListener(e -> info()); // 내정보
		rentedBooksButton.addActionListener(e -> rentedBooks()); // 대여중인 책
		logoutButton.addActionListener(e -> logout()); // 로그아웃

		setVisible(true);
	}

	
//	public MyPageUI(int a) {
//		if (a == 1) {
//            cart(); // 특정 조건에 맞게 cart() 호출
//        }
//	}
	private void library() {
		this.dispose();
		new UI2().setVisible(true);
	}

	public void cart() {
		mainPanel.removeAll();

		// 테이블 컬럼 설정
		columnNames = new String[] { "책 번호", "책 이름", "작가", "출판사" };
		tableModel = new DefaultTableModel(columnNames, 0);
		bookTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(bookTable);

		// cartPanel을 새로 생성하여 구성
		cartPanel = new JPanel();
		cartPanel.setLayout(new BorderLayout());

		cartPanel.add(new JLabel("목록"), BorderLayout.NORTH);
		cartPanel.add(scrollPane, BorderLayout.CENTER);

		// mainPanel에 cartPanel을 추가
		mainPanel.add(cartPanel, BorderLayout.CENTER);

		// UI 갱신
		mainPanel.revalidate(); // 레이아웃 재계산

		// ---------------------------------------------------

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
				""";

		// 테이블 초기화
		tableModel.setRowCount(0); // 기존 데이터 삭제

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Object[] row = { rs.getInt("BOOKNO"), rs.getString("BOOKNAME"), rs.getString("AUTHOR"),
						rs.getString("PUBLISHER") };
				tableModel.addRow(row); // 테이블에 데이터 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void info() {

	}

	private void rentedBooks() {
		mainPanel.removeAll();

		// 새로운 테이블 및 스크롤 패널 생성
		columnNames = new String[] { "대여번호", "제목", "대여시작일", "대여종료일", "상태" };
		tableModel = new DefaultTableModel(columnNames, 0);
		JTable boardTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(boardTable);

		// ViewPanel을 새로 구성하여 mainPanel에 추가
		ViewPanel = new JPanel();
		ViewPanel.setLayout(new BorderLayout());

		ViewPanel.add(new JLabel("목록"), BorderLayout.NORTH);
		ViewPanel.add(scrollPane, BorderLayout.CENTER);

		// mainPanel에 ViewPanel을 추가 (왼쪽에 위치하도록 설정)
		mainPanel.add(ViewPanel, BorderLayout.WEST);

		// UI 갱신
		mainPanel.revalidate();
		mainPanel.repaint();

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
		new MyPageUI();
	}
}
