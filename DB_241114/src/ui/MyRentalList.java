package ui;


import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.MyRentalDAO;
import dto.MyRentalDTO;

public class MyRentalList extends JFrame {
	private JFrame frame;
	
	// 테이블 모델 생성
	private DefaultTableModel rentalBookTableModel;
	private JTable rentalBookTable;

	
	MyRentalDAO dao = null;
	
	
	
	public MyRentalList() {
	    // JFrame 설정 
		frame = new JFrame("My Rental List");
		frame.setSize(900, 600);
		frame.setLocationRelativeTo(null); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // 메인 프레임에 BorderLayout 설정
        
        frame.add(new JLabel("나의 대여 목록"), BorderLayout.NORTH);
        
        
        // 테이블 모델 초기화 (열 이름 정의)
        String[] columnNames = {"대여번호", "책이름", "대여시작일", "대여종료일", "상태"};
        rentalBookTableModel = new DefaultTableModel(columnNames, 0);
        
        // JTable 생성
        rentalBookTable = new JTable(rentalBookTableModel);
        rentalBookTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

	    // 테스트 데이터를 테이블 모델에 추가
	    JScrollPane scrollPane = new JScrollPane(rentalBookTable);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    
	    // JFrame 설정
        frame.setVisible(true); // 모든 설정 후 창 보이기
        
        refreshTable();
        
	}


	// 테이블 갱신 메서드
	public void refreshTable() {
	    // DAO 객체 초기화
	    if (dao == null) {
	        dao = new MyRentalDAO();
	    }

	    // 이전 데이터 제거
	    rentalBookTableModel.setRowCount(0);

	    // 대여 기록 조회
	    ArrayList<MyRentalDTO> resultList = dao.getRentalAllBooks();
	    
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
	            String rentalEndDateStr = rentalDTO.getReturnDate().split(" ")[0];  // 날짜만 추출
	            rentalEndDate = LocalDate.parse(rentalEndDateStr);  // 날짜만 LocalDate로 변환

	            // 대여 시작 날짜 처리 (시간 부분 제거)
	            String rentalStartDateStr = rentalDTO.getRentalDate().split(" ")[0];  // 날짜만 추출
	            rentalStartDate = LocalDate.parse(rentalStartDateStr);  // 날짜만 LocalDate로 변환
	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid date format: " + e.getMessage());
	        }

	        // 상태 계산
	        String status;
	        if (rentalEndDate != null && rentalEndDate.isBefore(currentDate)) {
	            status = "반납완료";
	        } else {
	            status = "대여중";
	        }

	        // 날짜 출력 형식 변경
	        String formattedRentalStartDate = rentalStartDate != null ? rentalStartDate.format(formatter) : "N/A";
	        String formattedReturnDate = rentalEndDate != null ? rentalEndDate.format(formatter) : "N/A";

	        Object[] rowData = {
	            rentalDTO.getRentalId(),
	            rentalDTO.getBookName(),
	            formattedRentalStartDate, // 대여 시작 날짜
	            formattedReturnDate, // 대여 종료 날짜
	            status // 상태를 추가
	        };
	        
	        rentalBookTableModel.addRow(rowData);
	    }
	}







	
	public static void main(String[] args) {
        new MyRentalList();
    }
}