package com.example.schedule.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.schedule.dto.schedule.ImportantUpdateRequest;
import com.example.schedule.dto.schedule.RepeatScheduleCreateRequest;
import com.example.schedule.dto.schedule.ScheduleCreateRequest;
import com.example.schedule.dto.schedule.ScheduleResponse;
import com.example.schedule.dto.schedule.ScheduleSummaryResponse;
import com.example.schedule.dto.schedule.ScheduleUpdateRequest;
import com.example.schedule.service.ScheduleService;

import jakarta.servlet.http.HttpSession;

@RestController
public class ScheduleController {

	private final ScheduleService scheduleService;

	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	// session에서 로그인 사용자 id 꺼내기
	private Long getLoginUserId(HttpSession session) {
		return (Long) session.getAttribute("loginUserId");
	}

	// 일정 목록 조회
	@GetMapping("/api/schedules")
	public List<ScheduleResponse> getSchedules(HttpSession session) {
		Long userId = getLoginUserId(session);
		return scheduleService.getSchedules(userId);
	}

	// 일정 등록
	@PostMapping("/api/schedules")
	public ResponseEntity<?> createSchedule(@RequestBody ScheduleCreateRequest request, HttpSession session) {
		try {
			Long userId = getLoginUserId(session);
			ScheduleResponse response = scheduleService.createSchedule(userId, request);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(createErrorBody(e.getMessage()));
		}
	}

	// 일정 수정
	@PutMapping("/api/schedules/{scheduleId}")
	public ResponseEntity<?> updateSchedule(@PathVariable Long scheduleId,
											@RequestBody ScheduleUpdateRequest request,
											HttpSession session) {
		try {
			Long userId = getLoginUserId(session);
			ScheduleResponse response = scheduleService.updateSchedule(userId, scheduleId, request);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(createErrorBody(e.getMessage()));
		}
	}

	// 일정 삭제
	@DeleteMapping("/api/schedules/{scheduleId}")
	public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId, HttpSession session) {
		try {
			Long userId = getLoginUserId(session);
			scheduleService.deleteSchedule(userId, scheduleId);

			Map<String, Object> result = new HashMap<>();
			result.put("success", true);
			result.put("message", "일정이 삭제되었습니다.");

			return ResponseEntity.ok(result);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(createErrorBody(e.getMessage()));
		}
	}

	// 중요도 변경
	@PatchMapping("/api/schedules/{scheduleId}/important")
	public ResponseEntity<?> updateImportant(@PathVariable Long scheduleId,
											 @RequestBody ImportantUpdateRequest request,
											 HttpSession session) {
		try {
			Long userId = getLoginUserId(session);
			ScheduleResponse response = scheduleService.updateImportant(userId, scheduleId, request);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(createErrorBody(e.getMessage()));
		}
	}

	// 반복 일정 추가
	@PostMapping("/api/schedules/repeat")
	public ResponseEntity<?> createRepeatSchedules(@RequestBody RepeatScheduleCreateRequest request,
												   HttpSession session) {
		try {
			Long userId = getLoginUserId(session);
			List<ScheduleResponse> response = scheduleService.createRepeatSchedules(userId, request);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(createErrorBody(e.getMessage()));
		}
	}

	// 가까운 일정 요약 조회
	@GetMapping("/api/schedules/summary")
	public ScheduleSummaryResponse getSummary(HttpSession session) {
		Long userId = getLoginUserId(session);
		return scheduleService.getSummary(userId);
	}

	// 에러 응답 body 생성용 공통 메서드
	private Map<String, Object> createErrorBody(String message) {
		Map<String, Object> result = new HashMap<>();
		result.put("success", false);
		result.put("message", message);
		return result;
	}
}