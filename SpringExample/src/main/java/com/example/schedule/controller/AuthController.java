package com.example.schedule.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schedule.config.LoginCheckInterceptor;
import com.example.schedule.dto.auth.LoginRequest;
import com.example.schedule.dto.auth.LoginResponse;
import com.example.schedule.service.AuthService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request, HttpSession session) {
        LoginResponse response = authService.login(request, session);

        if (response.isSuccess()) {
            session.setAttribute(LoginCheckInterceptor.LOGIN_USER, response.getUsername());
            session.setAttribute("DISPLAY_NAME", response.getDisplayName());
            session.setAttribute("USER_ID", response.getUserId());
        }

        return response;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "로그아웃 되었습니다.");
        return result;
    }
}