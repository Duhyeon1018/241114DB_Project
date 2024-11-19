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
		String sql = "INSERT INTO KUSERTABLE (USERID, USERNAME, USERPASSWORD) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getUserId());
			stmt.setString(2, user.getUsername());
			stmt.setString(3, user.getPassword());
			return stmt.executeUpdate() > 0; // 데이터 삽입 성공 여부 확인
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// UserDAO 클래스에 메서드 추가
	public int getUserNoById(String memberID) {
	    int userNo = -1;
	    try {
	        String sql = "SELECT USERNO FROM KUSERTABLE WHERE USERID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, memberID);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            userNo = rs.getInt("USERNO");  // USERNO 컬럼에서 사용자 번호 가져오기
	        }

	        rs.close();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return userNo;
	}
  
	// 사용자 이름가져오는 메서드
	public String getUserNameById(String userId) {
		String sql = "SELECT USERNAME FROM KUSERTABLE WHERE USERID = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("USERNAME"); // 사용자 이름 반환
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // 사용자 이름을 찾지 못한 경우 null 반환
	}

}