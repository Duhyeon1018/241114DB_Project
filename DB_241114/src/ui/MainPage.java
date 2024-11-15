package ui;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UserDAO;
import dao.CgwDAO;

public class MainPage extends JFrame {

	private UserDAO userDAO; // 인스턴스 선언
	private String userID;
	private String userName;
	private int userNo;

	// 하나의 생성자로 통합, 필요한 매개변수를 설정
	public MainPage(String userID, String userName, int userNo) {
		this.userID = userID;
		this.userName = userName;
		this.userNo = userNo;

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
				new UI2Cgw(userID, userName); // 도서관 페이지 열기
				dispose(); // MainPage 창 닫기
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

		withdrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "정말로 회원 탈퇴를 하시겠습니까?", "회원 탈퇴",
						JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					deleteUserFromDB(userNo);
					JOptionPane.showMessageDialog(null, "회원 탈퇴가 완료되었습니다.");
					handleLogout();
				}
			}
		});

		// 패널에 컴포넌트 추가
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(userLabel); // 환영 메시지 추가
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
		mainPanel.add(Box.createVerticalStrut(10));

		add(mainPanel);
		setVisible(true);
	}

	// 로그아웃 메서드
	private void handleLogout() {
		new LoginUI().setVisible(true);
		dispose();
	}

	// DB에서 사용자 정보 삭제하는 메서드
	private void deleteUserFromDB(int userNo) {
		String sql = "DELETE FROM KUSERTABLE WHERE USERNO = " + userNo;
		CgwDAO.executeUpdateSql(sql); // SQL 실행하여 사용자 정보 삭제
	}

	public static void main(String[] args) {
		// 로그인된 사용자의 ID와 이름을 예시로 전달
		new MainPage("12345", "홍길동", 1);
	}
}
