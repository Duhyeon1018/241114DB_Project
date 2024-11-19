package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.CgwDAO;
import dao.UserDAO;

public class MainPage extends JFrame {

	private UserDAO userDAO; // 인스턴스 선언
	private String userID;
	private String userName;

	public MainPage(String userID, String userName) {
		this.userID = userID; // 필드에 userID 초기화
		this.userName = userName;
		
		setTitle("메인페이지");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 가운데에 창 배치

		userDAO = new UserDAO(); // 인스턴스 초기화

// 메인 패널 설정
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

// 사용자 환영 메시지 설정
		JLabel userLabel = new JLabel(userName + "님, 환영합니다!");
		userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

// 버튼 생성 및 이벤트 추가
		JButton bookRegisterButton = new JButton("도서관");
		JButton myLibraryButton = new JButton("나의 대여목록");
		JButton cartButton = new JButton("장바구니");
		JButton logoutButton = new JButton("로그아웃");
		JButton withdrawButton = new JButton("회원탈퇴");

// 버튼의 크기 통일
		Dimension buttonSize = new Dimension(200, 40);
		bookRegisterButton.setMaximumSize(buttonSize);
		myLibraryButton.setMaximumSize(buttonSize);
		cartButton.setMaximumSize(buttonSize);
		logoutButton.setMaximumSize(buttonSize);
		withdrawButton.setMaximumSize(buttonSize);

		bookRegisterButton.addActionListener(e -> movelibrary()); // 도서관이동
		myLibraryButton.addActionListener(e -> myLibrary()); // 장바구니
		cartButton.addActionListener(e -> cart(1));
		logoutButton.addActionListener(e -> handleLogout());
		withdrawButton.addActionListener(e -> withdraw());

		buttonPanel.add(Box.createVerticalStrut(20));
		buttonPanel.add(userLabel); // 환영 메시지 먼저 추가
		buttonPanel.add(Box.createVerticalStrut(20));
		buttonPanel.add(bookRegisterButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(myLibraryButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(cartButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(logoutButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(withdrawButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		add(buttonPanel);

// 프레임에 패널 추가
		add(buttonPanel);

		setVisible(true);

	}

//도서관
	private void movelibrary() {
		this.dispose();
		new UI2Cgw("id", "pas").setVisible(true);
	}

//마이페이-대여목록
	private void myLibrary() {
		this.dispose();
		new MyRentalList().setVisible(true);
	}

//마이페이지-장바구니
	public void cart(int a) {
		this.dispose();
		new MyPageUI(a).setVisible(true);
	}

//로그아웃
	private void handleLogout() {
		new LoginUI().setVisible(true);
		dispose();
	}

	private void withdraw() {
		int confirm = JOptionPane.showConfirmDialog(null, "정말로 회원 탈퇴를 하시겠습니까?", "회원 탈퇴", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			int num = getUserNo(userID);
			deleteUserFromDB(num);
			JOptionPane.showMessageDialog(null, "회원 탈퇴가 완료되었습니다.");
			dispose(); // 현재 창 닫기
			new LoginUI(); // 로그인 페이지로 이동 (회원 탈퇴 후)
		}

	}

	private int getUserNo(String userID) {
		String sql = "SELECT USERNO FROM KUSERTABLE WHERE USERID = ?";
		int userNo = -1; // 기본값으로 -1을 설정

// 데이터베이스 연결 설정
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "scott";
		String password = "tiger";

		try (Connection con = DriverManager.getConnection(url, userid, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, userID); // ?에 userID 값 설정
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					userNo = rs.getInt("USERNO"); // 결과에서 USERNO를 읽어서 저장
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userNo; // 조회된 userNo 반환
	}

	private void deleteUserFromDB(int userNo) {
		String sql = "DELETE FROM KUSERTABLE WHERE USERNO = " + userNo;
// 해당 SQL을 실행하여 DB에서 사용자 정보 삭제
		CgwDAO.executeUpdateSql(sql);
	}

	public static void main(String[] args) {
// 로그인된 사용자의 ID와 이름을 예시로 전달 ("12345"와 "홍길동"으로 가정)
		String loggedInUserId = "1";
		String loggedInUsername = "홍길동";
		new MainPage(loggedInUserId, loggedInUsername);
	}
}