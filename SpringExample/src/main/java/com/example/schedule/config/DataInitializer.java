package com.example.schedule.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.schedule.entity.RepeatType;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

	private static final Long USER1_ID = 1L;
	private static final Long USER2_ID = 2L;

	private final ScheduleRepository scheduleRepository;

	public DataInitializer(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	@Override
	public void run(String... args) {
		seedSchedules();
	}

	private void seedSchedules() {
		// user1 일정이 이미 있으면 중복 삽입 방지
		if (!scheduleRepository.findAllByUserId(USER1_ID).isEmpty()) {
			return;
		}

		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

		// user1의 초기 일정 7개
		saveSchedule(
				USER1_ID,
				"데일리 스탠드업",
				"실행 직후 요약 박스에 가장 먼저 보이도록 만든 일정",
				now.plusHours(1),
				now.plusHours(1).plusMinutes(30),
				true,
				RepeatType.NONE
		);

		saveSchedule(
				USER1_ID,
				"시연 리허설",
				"수정/중요도 변경 기능을 확인하기 좋은 일정",
				now.plusHours(3),
				now.plusHours(4),
				false,
				RepeatType.NONE
		);

		saveSchedule(
				USER1_ID,
				"내일 팀 미팅",
				"오늘/내일/다가오는 일정 흐름 확인용",
				atDaysLater(1, 10, 0),
				atDaysLater(1, 11, 0),
				true,
				RepeatType.NONE
		);

		saveSchedule(
				USER1_ID,
				"아침 운동",
				"반복 일정 예시 (DAILY) - 1회차",
				atDaysLater(2, 7, 30),
				atDaysLater(2, 8, 0),
				false,
				RepeatType.DAILY
		);

		saveSchedule(
				USER1_ID,
				"아침 운동",
				"반복 일정 예시 (DAILY) - 2회차",
				atDaysLater(3, 7, 30),
				atDaysLater(3, 8, 0),
				false,
				RepeatType.DAILY
		);

		saveSchedule(
				USER1_ID,
				"주간 회고",
				"반복 일정 예시 (WEEKLY)",
				atDaysLater(5, 16, 0),
				atDaysLater(5, 17, 0),
				false,
				RepeatType.WEEKLY
		);

		saveSchedule(
				USER1_ID,
				"발표 자료 마감",
				"삭제/수정/중요 일정 요약 확인용",
				atDaysLater(7, 18, 0),
				atDaysLater(7, 19, 0),
				true,
				RepeatType.NONE
		);

		// user2에도 1개 정도 넣고 싶다면 선택적으로 추가 가능
		saveSchedule(
				USER2_ID,
				"user2 테스트 일정",
				"user2 로그인 확인용 일정",
				atDaysLater(1, 14, 0),
				atDaysLater(1, 15, 0),
				false,
				RepeatType.NONE
		);
	}

	private void saveSchedule(Long userId,
							  String title,
							  String description,
							  LocalDateTime startAt,
							  LocalDateTime endAt,
							  boolean important,
							  RepeatType repeatType) {
		Schedule schedule = new Schedule();
		schedule.setUserId(userId);
		schedule.setTitle(title);
		schedule.setDescription(description);
		schedule.setStartAt(startAt);
		schedule.setEndAt(endAt);
		schedule.setImportant(important);
		schedule.setRepeatType(repeatType);

		scheduleRepository.save(schedule);
	}

	private LocalDateTime atDaysLater(int days, int hour, int minute) {
		return LocalDate.now().plusDays(days).atTime(hour, minute);
	}
	
}