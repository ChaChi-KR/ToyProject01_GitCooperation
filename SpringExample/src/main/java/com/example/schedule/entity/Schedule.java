package com.example.schedule.entity;

import java.time.LocalDateTime;

public class Schedule {

	// 일정 고유 번호
	private Long id;

	// 이 일정의 소유 사용자 번호
	private Long userId;

	// 일정 제목
	private String title;

	// 일정 상세 설명
	private String description;

	// 시작 일시
	private LocalDateTime startAt;

	// 종료 일시
	private LocalDateTime endAt;

	// 중요 여부
	private boolean important;

	// 반복 유형
	private RepeatType repeatType;

	public Schedule() {
	}

	public Schedule(Long id, Long userId, String title, String description,
					LocalDateTime startAt, LocalDateTime endAt,
					boolean important, RepeatType repeatType) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.startAt = startAt;
		this.endAt = endAt;
		this.important = important;
		this.repeatType = repeatType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
}