package com.example.schedule.dto.schedule;

import java.time.LocalDateTime;

import com.example.schedule.entity.RepeatType;

public class RepeatScheduleCreateRequest {

	// 일정 제목
	private String title;

	// 일정 설명
	private String description;

	// 첫 일정의 시작 일시
	private LocalDateTime startAt;

	// 첫 일정의 종료 일시
	private LocalDateTime endAt;

	// 중요 여부
	private boolean important;

	// 반복 유형 (DAILY / WEEKLY)
	private RepeatType repeatType;

	// 몇 번 생성할 것인지
	private int repeatCount;

	public RepeatScheduleCreateRequest() {
	}

	public RepeatScheduleCreateRequest(String title, String description,
									   LocalDateTime startAt, LocalDateTime endAt,
									   boolean important, RepeatType repeatType,
									   int repeatCount) {
		this.title = title;
		this.description = description;
		this.startAt = startAt;
		this.endAt = endAt;
		this.important = important;
		this.repeatType = repeatType;
		this.repeatCount = repeatCount;
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

	public RepeatType getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(RepeatType repeatType) {
		this.repeatType = repeatType;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}
}