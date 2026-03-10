package com.example.schedule.dto.schedule;

import java.time.LocalDateTime;

import com.example.schedule.entity.RepeatType;
import com.example.schedule.entity.Schedule;

public class ScheduleResponse {

	private Long id;
	private String title;
	private String description;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private boolean important;
	private RepeatType repeatType;

	public ScheduleResponse() {
	}

	public ScheduleResponse(Long id, String title, String description,
							LocalDateTime startAt, LocalDateTime endAt,
							boolean important, RepeatType repeatType) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.startAt = startAt;
		this.endAt = endAt;
		this.important = important;
		this.repeatType = repeatType;
	}

	// 엔티티를 응답 DTO로 바꾸기 위한 편의 메서드
	public static ScheduleResponse from(Schedule schedule) {
		return new ScheduleResponse(
				schedule.getId(),
				schedule.getTitle(),
				schedule.getDescription(),
				schedule.getStartAt(),
				schedule.getEndAt(),
				schedule.isImportant(),
				schedule.getRepeatType()
		);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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