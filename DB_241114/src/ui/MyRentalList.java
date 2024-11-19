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

public class MyRentalList extends JFrame {
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
	
	public MyRentalList() {
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
		
		
		add(mainPanel, BorderLayout.CENTER); // 메인 패널 추가


		mainButton.addActionListener(e -> mainPage()); 
		
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


	public static void main(String[] args) {
		new MyRentalList();
	}
}