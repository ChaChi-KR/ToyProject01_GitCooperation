package com.example.schedule.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.schedule.entity.User;

import jakarta.annotation.PostConstruct;

@Repository
public class UserRepository_before {

	// 사용자 id 기준 저장소
	private final Map<Long, User> userStore = new HashMap<>();

	// username 기준 빠른 조회를 위한 저장소
	private final Map<String, User> userByUsernameStore = new HashMap<>();

	// 애플리케이션 시작 시 mock 사용자 등록
	@PostConstruct
	public void init() {
		User user1 = new User(1L, "user1", "1234", "사용자1");
		User user2 = new User(2L, "user2", "1234", "사용자2");

		save(user1);
		save(user2);
	}

	// 사용자 저장
	public User save(User user) {
		userStore.put(user.getId(), user);
		userByUsernameStore.put(user.getUsername(), user);
		return user;
	}

	// id로 사용자 조회
	public User findById(Long id) {
		return userStore.get(id);
	}

	// username으로 사용자 조회
	public User findByUsername(String username) {
		return userByUsernameStore.get(username);
	}

	// 전체 사용자 목록
	public Collection<User> findAll() {
		return userStore.values();
	}
}