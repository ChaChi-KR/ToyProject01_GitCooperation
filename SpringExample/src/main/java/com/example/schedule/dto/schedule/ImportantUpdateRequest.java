package com.example.schedule.dto.schedule;

public class ImportantUpdateRequest {

	// 변경할 중요 여부
	private boolean important;

	public ImportantUpdateRequest() {
	}

	public ImportantUpdateRequest(boolean important) {
		this.important = important;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}
}