package com.example.schedule.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.schedule.entity.RepeatType;
import com.example.schedule.entity.Schedule;

import jakarta.annotation.PostConstruct;

@Repository
public class ScheduleRepository_before {

	// 일정 id 자동 증가용 시퀀스
	private final AtomicLong sequence = new AtomicLong(1L);

	// 일정 저장소
	private final Map<Long, Schedule> scheduleStore = new HashMap<>();

	// 애플리케이션 시작 시 예시 일정 등록
	@PostConstruct
	public void init() {
		save(new Schedule(
				null,
				1L,
				"팀 미팅",
				"주간 진행 상황 공유",
				LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0),
				LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0),
				true,
				RepeatType.NONE
		));

		save(new Schedule(
				null,
				1L,
				"운동",
				"저녁 러닝",
				LocalDateTime.now().plusDays(2).withHour(19).withMinute(0).withSecond(0).withNano(0),
				LocalDateTime.now().plusDays(2).withHour(20).withMinute(0).withSecond(0).withNano(0),
				false,
				RepeatType.NONE
		));
	}

	// 일정 저장
	// id가 없으면 새 일정으로 보고 id를 발급
	public Schedule save(Schedule schedule) {
		if (schedule.getId() == null) {
			schedule.setId(sequence.getAndIncrement());
		}
		scheduleStore.put(schedule.getId(), schedule);
		return schedule;
	}

	// 일정 단건 조회
	public Schedule findById(Long scheduleId) {
		return scheduleStore.get(scheduleId);
	}

	// 특정 사용자의 전체 일정 조회 (시작 시간 기준 정렬)
	public List<Schedule> findAllByUserId(Long userId) {
		return scheduleStore.values().stream()
				.filter(schedule -> schedule.getUserId().equals(userId))
				.sorted(Comparator.comparing(Schedule::getStartAt))
				.collect(Collectors.toList());
	}

	// 특정 사용자의 일정 단건 조회
	// userId도 함께 확인하여 다른 사용자의 일정 접근을 막음
	public Schedule findByIdAndUserId(Long scheduleId, Long userId) {
		Schedule schedule = scheduleStore.get(scheduleId);

		if (schedule == null) {
			return null;
		}

		if (!schedule.getUserId().equals(userId)) {
			return null;
		}

		return schedule;
	}

	// 일정 삭제
	public boolean deleteByIdAndUserId(Long scheduleId, Long userId) {
		Schedule schedule = findByIdAndUserId(scheduleId, userId);

		if (schedule == null) {
			return false;
		}

		scheduleStore.remove(scheduleId);
		return true;
	}

	// 여러 일정 저장
	public List<Schedule> saveAll(List<Schedule> schedules) {
		List<Schedule> result = new ArrayList<>();

		for (Schedule schedule : schedules) {
			result.add(save(schedule));
		}

		return result;
	}
}