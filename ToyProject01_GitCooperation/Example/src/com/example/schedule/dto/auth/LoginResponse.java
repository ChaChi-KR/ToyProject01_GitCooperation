package com.example.schedule.dto.auth;

public class LoginResponse {

	// 로그인 성공 여부
	private boolean success;

	// 안내 메시지
	private String message;

	// 로그인 성공 시 사용자 정보
	private Long userId;
	private String username;
	private String displayName;

	public LoginResponse() {
	}

	public LoginResponse(boolean success, String message, Long userId, String username, String displayName) {
		this.success = success;
		this.message = message;
		this.userId = userId;
		this.username = username;
		this.displayName = displayName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}