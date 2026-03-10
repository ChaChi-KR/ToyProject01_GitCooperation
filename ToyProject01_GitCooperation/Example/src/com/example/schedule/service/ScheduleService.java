package com.example.schedule.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.schedule.dto.schedule.ImportantUpdateRequest;
import com.example.schedule.dto.schedule.RepeatScheduleCreateRequest;
import com.example.schedule.dto.schedule.ScheduleCreateRequest;
import com.example.schedule.dto.schedule.ScheduleResponse;
import com.example.schedule.dto.schedule.ScheduleSummaryResponse;
import com.example.schedule.dto.schedule.ScheduleUpdateRequest;
import com.example.schedule.entity.RepeatType;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;

@Service
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;

	public ScheduleService(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	// 일정 목록 조회
	public List<ScheduleResponse> getSchedules(Long userId) {
		List<Schedule> schedules = scheduleRepository.findAllByUserId(userId);
		List<ScheduleResponse> result = new ArrayList<>();

		for (Schedule schedule : schedules) {
			result.add(ScheduleResponse.from(schedule));
		}

		return result;
	}

	// 일정 등록
	public ScheduleResponse createSchedule(Long userId, ScheduleCreateRequest request) {
		Schedule schedule = new Schedule(
				null,
				userId,
				request.getTitle(),
				request.getDescription(),
				request.getStartAt(),
				request.getEndAt(),
				request.isImportant(),
				RepeatType.NONE
		);

		Schedule saved = scheduleRepository.save(schedule);
		return ScheduleResponse.from(saved);
	}

	// 일정 수정
	public ScheduleResponse updateSchedule(Long userId, Long scheduleId, ScheduleUpdateRequest request) {
		Schedule schedule = scheduleRepository.findByIdAndUserId(scheduleId, userId);

		if (schedule == null) {
			throw new IllegalArgumentException("해당 일정을 찾을 수 없습니다.");
		}

		schedule.setTitle(request.getTitle());
		schedule.setDescription(request.getDescription());
		schedule.setStartAt(request.getStartAt());
		schedule.setEndAt(request.getEndAt());
		schedule.setImportant(request.isImportant());

		Schedule updated = scheduleRepository.save(schedule);
		return ScheduleResponse.from(updated);
	}

	// 일정 삭제
	public void deleteSchedule(Long userId, Long scheduleId) {
		boolean deleted = scheduleRepository.deleteByIdAndUserId(scheduleId, userId);

		if (!deleted) {
			throw new IllegalArgumentException("삭제할 일정을 찾을 수 없습니다.");
		}
	}

	// 중요도 변경
	public ScheduleResponse updateImportant(Long userId, Long scheduleId, ImportantUpdateRequest request) {
		Schedule schedule = scheduleRepository.findByIdAndUserId(scheduleId, userId);

		if (schedule == null) {
			throw new IllegalArgumentException("해당 일정을 찾을 수 없습니다.");
		}

		schedule.setImportant(request.isImportant());

		Schedule updated = scheduleRepository.save(schedule);
		return ScheduleResponse.from(updated);
	}

	// 반복 일정 생성
	public List<ScheduleResponse> createRepeatSchedules(Long userId, RepeatScheduleCreateRequest request) {
		List<Schedule> schedules = new ArrayList<>();

		// repeatCount가 1보다 작으면 잘못된 요청으로 처리
		if (request.getRepeatCount() < 1) {
			throw new IllegalArgumentException("repeatCount는 1 이상이어야 합니다.");
		}

		// NONE으로 들어오면 반복 일정이 아니라 일반 일정처럼 1건만 생성
		if (request.getRepeatType() == null || request.getRepeatType() == RepeatType.NONE) {
			Schedule schedule = new Schedule(
					null,
					userId,
					request.getTitle(),
					request.getDescription(),
					request.getStartAt(),
					request.getEndAt(),
					request.isImportant(),
					RepeatType.NONE
			);
			schedules.add(schedule);
		} else {
			for (int i = 0; i < request.getRepeatCount(); i++) {
				LocalDateTime startAt = request.getStartAt();
				LocalDateTime endAt = request.getEndAt();

				if (request.getRepeatType() == RepeatType.DAILY) {
					startAt = startAt.plusDays(i);
					endAt = endAt.plusDays(i);
				} else if (request.getRepeatType() == RepeatType.WEEKLY) {
					startAt = startAt.plusWeeks(i);
					endAt = endAt.plusWeeks(i);
				}

				Schedule schedule = new Schedule(
						null,
						userId,
						request.getTitle(),
						request.getDescription(),
						startAt,
						endAt,
						request.isImportant(),
						request.getRepeatType()
				);

				schedules.add(schedule);
			}
		}

		List<Schedule> savedSchedules = scheduleRepository.saveAll(schedules);
		List<ScheduleResponse> result = new ArrayList<>();

		for (Schedule schedule : savedSchedules) {
			result.add(ScheduleResponse.from(schedule));
		}

		return result;
	}

	// 가까운 일정 요약 조회
	public ScheduleSummaryResponse getSummary(Long userId) {
		List<Schedule> schedules = scheduleRepository.findAllByUserId(userId);

		LocalDateTime now = LocalDateTime.now();

		int upcomingCount = 0;
		int importantCount = 0;

		Schedule nearestSchedule = null;

		for (Schedule schedule : schedules) {
			// 현재 이후의 일정만 upcoming으로 간주
			if (schedule.getStartAt().isAfter(now) || schedule.getStartAt().isEqual(now)) {
				upcomingCount++;

				if (nearestSchedule == null || schedule.getStartAt().isBefore(nearestSchedule.getStartAt())) {
					nearestSchedule = schedule;
				}
			}

			if (schedule.isImportant()) {
				importantCount++;
			}
		}

		if (nearestSchedule == null) {
			return new ScheduleSummaryResponse(
					upcomingCount,
					importantCount,
					null,
					null
			);
		}

		return new ScheduleSummaryResponse(
				upcomingCount,
				importantCount,
				nearestSchedule.getTitle(),
				nearestSchedule.getStartAt()
		);
	}
}