package com.example.schedule.entity;

public class User {

	// 사용자 고유 번호
	private Long id;

	// 로그인 아이디
	private String username;

	// 로그인 비밀번호
	private String password;

	// 화면에 표시할 이름
	private String displayName;

	public User() {
	}

	public User(Long id, String username, String password, String displayName) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.displayName = displayName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}