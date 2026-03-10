package com.example.schedule.dto.schedule;

import java.time.LocalDateTime;

public class ScheduleSummaryResponse {

	// 앞으로 남아 있는 일정 개수
	private int upcomingCount;

	// 중요 일정 개수
	private int importantCount;

	// 가장 가까운 일정 제목
	private String nearestTitle;

	// 가장 가까운 일정 시작 시간
	private LocalDateTime nearestStartAt;

	public ScheduleSummaryResponse() {
	}

	public ScheduleSummaryResponse(int upcomingCount, int importantCount,
								   String nearestTitle, LocalDateTime nearestStartAt) {
		this.upcomingCount = upcomingCount;
		this.importantCount = importantCount;
		this.nearestTitle = nearestTitle;
		this.nearestStartAt = nearestStartAt;
	}

	public int getUpcomingCount() {
		return upcomingCount;
	}

	public void setUpcomingCount(int upcomingCount) {
		this.upcomingCount = upcomingCount;
	}

	public int getImportantCount() {
		return importantCount;
	}

	public void setImportantCount(int importantCount) {
		this.importantCount = importantCount;
	}

	public String getNearestTitle() {
		return nearestTitle;
	}

	public void setNearestTitle(String nearestTitle) {
		this.nearestTitle = nearestTitle;
	}

	public LocalDateTime getNearestStartAt() {
		return nearestStartAt;
	}

	public void setNearestStartAt(LocalDateTime nearestStartAt) {
		this.nearestStartAt = nearestStartAt;
	}
}