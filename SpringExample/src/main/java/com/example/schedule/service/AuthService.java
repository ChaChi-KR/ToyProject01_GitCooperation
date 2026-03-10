package com.example.schedule.service;

import org.springframework.stereotype.Service;

import com.example.schedule.dto.auth.LoginRequest;
import com.example.schedule.dto.auth.LoginResponse;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {

	private final UserRepository userRepository;

	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public LoginResponse login(LoginRequest request, HttpSession session) {
		User user = userRepository.findByUsername(request.getUsername());

		if (user == null || !user.getPassword().equals(request.getPassword())) {
			return new LoginResponse(
					false,
					"아이디 또는 비밀번호가 올바르지 않습니다.",
					null,
					null,
					null
			);
		}

		// 세션 저장
		session.setAttribute("LOGIN_USER", user.getUsername());
		session.setAttribute("DISPLAY_NAME", user.getDisplayName());
		session.setAttribute("USER_ID", user.getId());

		return new LoginResponse(
				true,
				"로그인에 성공했습니다.",
				user.getId(),
				user.getUsername(),
				user.getDisplayName()
		);
	}
}