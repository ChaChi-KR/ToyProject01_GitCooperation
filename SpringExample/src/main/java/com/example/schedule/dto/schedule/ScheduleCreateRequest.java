package com.example.schedule.dto.schedule;

import java.time.LocalDateTime;

public class ScheduleCreateRequest {

	// 일정 제목
	private String title;

	// 일정 설명
	private String description;

	// 시작 일시
	private LocalDateTime startAt;

	// 종료 일시
	private LocalDateTime endAt;

	// 중요 여부
	private boolean important;

	public ScheduleCreateRequest() {
	}

	public ScheduleCreateRequest(String title, String description, LocalDateTime startAt,
								 LocalDateTime endAt, boolean important) {
		this.title = title;
		this.description = description;
		this.startAt = startAt;
		this.endAt = endAt;
		this.important = important;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}
}