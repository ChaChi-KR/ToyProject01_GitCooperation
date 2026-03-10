package com.example.schedule.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class StartupGuideRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println();
        System.out.println("==========================================");
        System.out.println(" 일정 관리 프로젝트 시연용 실행 안내");
        System.out.println("==========================================");
        System.out.println("로그인 페이지: http://localhost:8080/login.html");
        System.out.println("일정 페이지   : http://localhost:8080/schedule.html");
        System.out.println();
        System.out.println("[시연용 계정]");
        System.out.println("1) demo  / 1234");
        System.out.println("2) admin / admin1234");
        System.out.println();
        System.out.println("[초기 적재 데이터]");
        System.out.println("- demo 계정에 일정 7건 자동 생성");
        System.out.println("- 오늘 일정, 내일 일정, 중요 일정, 반복 일정 예시 포함");
        System.out.println();
        System.out.println("[바로 확인할 기능]");
        System.out.println("1. 로그인");
        System.out.println("2. 요약 박스 확인");
        System.out.println("3. 일정 목록 조회");
        System.out.println("4. 일정 수정 / 삭제");
        System.out.println("5. 중요도 변경");
        System.out.println("6. 반복 일정 등록");
        System.out.println("7. 로그아웃");
        System.out.println("==========================================");
        System.out.println();
    }
}