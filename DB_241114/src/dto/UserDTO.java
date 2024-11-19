package dto;

public class UserDTO {
	private String userId; // 사용자 ID
	private String password; // 비밀번호
	private String username; // 사용자 이름

	// 기본 생성자
	public UserDTO() {
	}

	// 필요한 필드를 포함한 생성자
	public UserDTO(String userId, String password, String username) {
		this.userId = userId;
		this.password = password;
		this.username = username;
	}

	// Getters and Setters
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}