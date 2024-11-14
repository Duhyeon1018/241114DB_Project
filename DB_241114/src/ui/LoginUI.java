package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dao.UserDAO;

public class LoginUI extends JFrame {
	private JTextField memberIDField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton registerButton;
	private UserDAO userDAO; // 인스턴스 이름 수정1

	public LoginUI() {
		setTitle("전자도서관 로그인");
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		userDAO = new UserDAO(); // 인스턴스 초기화

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel memberIDLabel = new JLabel("아이디:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(memberIDLabel, gbc);

		memberIDField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(memberIDField, gbc);

		JLabel passwordLabel = new JLabel("비밀번호:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(passwordLabel, gbc);

		passwordField = new JPasswordField(15);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(passwordField, gbc);

		loginButton = new JButton("로그인");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(loginButton, gbc);

		registerButton = new JButton("회원가입");
		gbc.gridy = 3;
		panel.add(registerButton, gbc);

		// 로그인 버튼 이벤트
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleLogin();
			}
		});

		// 회원가입 버튼 이벤트 - 회원가입 창으로 이동
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openRegistrationWindow();
			}
		});

		add(panel);
	}

	private void handleLogin() {
		String memberID = memberIDField.getText();
		String password = new String(passwordField.getPassword());

		if (memberID.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean loginSuccessful = authenticateUser(memberID, password);
		if (loginSuccessful) {
			JOptionPane.showMessageDialog(this, "로그인 성공!", "알림", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 잘못되었습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void openRegistrationWindow() {
		// 회원가입 창을 열고 현재 창을 닫습니다.
		new RegiUI().setVisible(true);
		this.dispose(); // 현재 창을 닫습니다.
	}

	private boolean authenticateUser(String memberID, String password) {
		return userDAO.login(memberID, password); // UserDAO의 login 메서드를 호출하여 로그인 확인
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LoginUI().setVisible(true);
			}
		});
	}
}
