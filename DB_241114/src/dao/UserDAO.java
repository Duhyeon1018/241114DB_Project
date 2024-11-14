package dao;

import java.sql.*;
import dto.UserDTO;

public class UserDAO {
	private Connection conn;

	// DB 연결 설정
	public UserDAO() {
		try {
			// Oracle DB 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 로그인 메서드
	public boolean login(String userId, String password) {
		String sql = "SELECT * FROM KUSERTABLE WHERE USERID = ? AND USERPASSWORD = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, userId);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			return rs.next(); // 사용자 존재 여부 확인
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 회원가입 메서드
	public boolean register(UserDTO user) {
		String sql = "INSERT INTO KUSERTABLE (, USERID, USERNAME, USERPASSWORD, ) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getUserId());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getUsername());
			return stmt.executeUpdate() > 0; // 데이터 삽입 성공 여부 확인
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
