package com.example.schedule.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.schedule.dto.auth.LoginRequest;
import com.example.schedule.dto.auth.LoginResponse;
import com.example.schedule.service.AuthService;

import jakarta.servlet.http.HttpSession;

@RestController
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	// 로그인 처리
	@PostMapping("/api/auth/login")
	public LoginResponse login(@RequestBody LoginRequest request, HttpSession session) {
		LoginResponse response = authService.login(request);

		// 로그인 성공 시 session에 사용자 id 저장
		if (response.isSuccess()) {
			session.setAttribute("loginUserId", response.getUserId());
		}

		return response;
	}

	// 로그아웃 처리
	@PostMapping("/api/auth/logout")
	public Map<String, Object> logout(HttpSession session) {
		Map<String, Object> result = new HashMap<>();

		// 현재 세션을 무효화하여 로그인 상태 제거
		session.invalidate();

		result.put("success", true);
		result.put("message", "로그아웃되었습니다.");

		return result;
	}
}