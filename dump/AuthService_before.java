package com.example.schedule.service;

import org.springframework.stereotype.Service;

import com.example.schedule.dto.auth.LoginRequest;
import com.example.schedule.dto.auth.LoginResponse;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;

@Service
public class AuthService_before {

	private final UserRepository userRepository;

	public AuthService_before(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 로그인 처리
	public LoginResponse login(LoginRequest request) {
		// username으로 사용자 조회
		User user = userRepository.findByUsername(request.getUsername());

		// 사용자가 없으면 로그인 실패
		if (user == null) {
			return new LoginResponse(false, "존재하지 않는 아이디입니다.", null, null, null);
		}

		// 비밀번호가 다르면 로그인 실패
		if (!user.getPassword().equals(request.getPassword())) {
			return new LoginResponse(false, "비밀번호가 올바르지 않습니다.", null, null, null);
		}

		// 로그인 성공
		return new LoginResponse(
				true,
				"로그인에 성공했습니다.",
				user.getId(),
				user.getUsername(),
				user.getDisplayName()
		);
	}

	// id로 사용자 조회
	public User findUserById(Long userId) {
		return userRepository.findById(userId);
	}
}