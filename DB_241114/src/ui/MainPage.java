package ui;

import java.awt.Component;
import java.awt.Dimension;

import dao.CgwDAO; // DB 작업을 위한 CgwDAO 클래스
import javax.swing.*;
import java.awt.*;

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
		setSize(1060, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 가운데에 창 배치

		userDAO = new UserDAO(); // 인스턴스 초기화

// 메인 패널 설정
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

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

// 각 버튼에 대한 클릭 이벤트 설정
		bookRegisterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
// "도서관" 버튼 클릭 시 UI2 클래스의 인스턴스를 열도록 수정
				new UI2Cgw(userID, userName); // UI2 클래스의 창 열기
				dispose(); // MainPage 창 닫기 (선택 사항)
			}
		});

		myLibraryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "나의 미디어목록 페이지로 이동합니다.");
			}
		});

		cartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "장바구니 페이지로 이동합니다.");
			}
		});

		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
				handleLogout();
			}
		});

// 패널에 컴포넌트 추가
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(userLabel); // 환영 메시지 먼저 추가
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(bookRegisterButton);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(myLibraryButton);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(cartButton);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(logoutButton);
		mainPanel.add(Box.createVerticalStrut(10));
		add(mainPanel);

// 프레임에 패널 추가
		add(mainPanel);

		setVisible(true);
	}

// 로그아웃 메서드
	private void handleLogout() {
		new LoginUI().setVisible(true);
		dispose();
	}

	public static void main(String[] args) {
// 로그인된 사용자의 ID와 이름을 예시로 전달 ("12345"와 "홍길동"으로 가정)
		String loggedInUserId = "12345";
		String loggedInUsername = "홍길동";
		new MainPage(loggedInUserId, loggedInUsername);
	}

	private String username; // 사용자 이름
	private int userNo; // 사용자 번호 (DB에서 삭제 작업에 사용)

	public MainPage(String username, int userNo) { // 사용자 이름과 사용자 번호를 인자로 받음
		this.username = username;
		this.userNo = userNo;

		setTitle("메인페이지");
		setSize(1060, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 가운데에 창 배치

// 메인 패널 설정
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

// 사용자 환영 메시지 설정
		JLabel userLabel = new JLabel(username + "님, 환영합니다!");
		userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

// 버튼 생성 및 이벤트 추가
		JButton bookRegisterButton = new JButton("도서관");
		JButton myLibraryButton = new JButton("나의 대여목록");
		JButton cartButton = new JButton("장바구니");
		JButton logoutButton = new JButton("로그아웃");
		JButton withdrawButton = new JButton("회원탈퇴"); // 회원탈퇴 버튼 추가

// 버튼의 크기 통일
		Dimension buttonSize = new Dimension(200, 40);
		bookRegisterButton.setMaximumSize(buttonSize);
		myLibraryButton.setMaximumSize(buttonSize);
		cartButton.setMaximumSize(buttonSize);
		logoutButton.setMaximumSize(buttonSize);
		withdrawButton.setMaximumSize(buttonSize);

// 각 버튼에 대한 클릭 이벤트 설정
		bookRegisterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
// "도서관" 버튼 클릭 시 UI2 클래스의 인스턴스를 열도록 수정
				new UI2(); // UI2 클래스의 창 열기
				dispose(); // MainPage 창 닫기 (선택 사항)
			}
		});

		myLibraryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "나의 미디어목록 페이지로 이동합니다.");
			}
		});

		cartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "장바구니 페이지로 이동합니다.");
			}
		});

// 로그아웃 버튼 클릭 시 처리
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
// 로그아웃 처리: 사용자를 로그인 화면으로 리디렉션하거나, 세션 종료
				JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
				dispose(); // 현재 창 닫기 (로그아웃 후 로그인 화면으로 이동)
				new LoginUI().setVisible(true); // 로그인 페이지로 돌아가기
			}
		});
//
// // 회원탈퇴 버튼 클릭 시 처리
// withdrawButton.addActionListener(new ActionListener() {
// @Override
// public void actionPerformed(ActionEvent e) {
// int confirm = JOptionPane.showConfirmDialog(null, "정말로 회원 탈퇴를 하시겠습니까?", "회원 탈퇴", JOptionPane.YES_NO_OPTION);
// if (confirm == JOptionPane.YES_OPTION) {
// // DB에서 사용자 정보를 삭제
// deleteUserFromDB(userNo);
// JOptionPane.showMessageDialog(null, "회원 탈퇴가 완료되었습니다.");
// dispose(); // 현재 창 닫기
// new LoginUI(); // 로그인 페이지로 이동 (회원 탈퇴 후)
// }
// }
// });

// 패널에 컴포넌트 추가
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(userLabel);
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(bookRegisterButton);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(myLibraryButton);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(cartButton);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(logoutButton);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(withdrawButton); // 회원탈퇴 버튼 추가

// 프레임에 패널 추가
		add(mainPanel);

		setVisible(true);
	}

// // DB에서 사용자 정보 삭제하는 메서드
// private void deleteUserFromDB(int userNo) {
// String sql = "DELETE FROM KUSERTABLE WHERE USERNO = " + userNo;
// // 해당 SQL을 실행하여 DB에서 사용자 정보 삭제
// CgwDAO.executeUpdateSql(sql);
// }

	public static void main1(String[] args) {
// 로그인된 사용자의 이름과 번호를 예시로 전달 ("홍길동"과 번호 1)
		String loggedInUsername = "홍길동";
		int loggedInUserNo = 1; // 예시 사용자 번호
		new MainPage(loggedInUsername, loggedInUserNo);
	}

}