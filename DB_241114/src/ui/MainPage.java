package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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

		// 버튼의 크기 통일
		Dimension buttonSize = new Dimension(200, 40);
		bookRegisterButton.setMaximumSize(buttonSize);
		myLibraryButton.setMaximumSize(buttonSize);
		cartButton.setMaximumSize(buttonSize);
		logoutButton.setMaximumSize(buttonSize);

		bookRegisterButton.addActionListener(e -> movelibrary()); // 도서관이동
		myLibraryButton.addActionListener(e -> myLibrary()); // 장바구니
		cartButton.addActionListener(e -> cart()); // 내정보
		logoutButton.addActionListener(e -> handleLogout()); // 대여중인 책

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
		add(buttonPanel);

		// 프레임에 패널 추가
		add(buttonPanel);

		setVisible(true);

	}
	//도서관
	private void movelibrary() {
		this.dispose();
		new UI2().setVisible(true);
	}
	//마이페이-대여목록
	private void myLibrary() {
		this.dispose();
		new MyPageUI().setVisible(true);
	}
	//마이페이지-장바구니
	public void cart() {
		this.dispose();
		new MyPageUI().setVisible(true);
	}

	//로그아웃
	private void handleLogout() {
		new LoginUI().setVisible(true);
		dispose();
	}

	public static void main(String[] args) {
		// 로그인된 사용자의 ID와 이름을 예시로 전달 ("12345"와 "홍길동"으로 가정)
		String loggedInUserId = "1";
		String loggedInUsername = "홍길동";
		new MainPage(loggedInUserId, loggedInUsername);
	}
}
