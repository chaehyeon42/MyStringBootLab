package com.rookies5.myspringbootLab;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 1-4) MyPropRunner 클래스 작성하기
 * ApplicationRunner를 상속받으면 프로그램 실행 시 run() 메서드가 자동으로 실행됩니다.
 */
@Slf4j // slf4j 로그를 사용하기 위한 어노테이션
@Component // 스프링이 이 클래스를 자동으로 찾아서 관리하도록 설정
@RequiredArgsConstructor // final이 붙은 필드를 생성자로 자동 주입해줌
public class MyPropRunner implements ApplicationRunner {

    // 1-4) @Value 어노테이션을 사용하여 application.properties의 값을 직접 가져옴
    @Value("${myprop.username}")
    private String name;

    // 1-5) MyPropProperties 객체를 주입받아 사용 (Properties 클래스 방식)
    private final MyPropProperties myPropProperties;

    // 1-6) 설정한 프로파일(prod/test)에 따라 생성된 MyEnvironment 빈을 주입받음
    private final MyEnvironment myEnvironment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("========== 과제 출력 시작 ==========");

        // 1-4) @Value를 이용한 출력
        System.out.println("[1-4] @Value로 읽은 username: " + name);

        // 1-5) slf4j Logger를 이용한 출력 (log.info 사용)
        log.info("[1-5] Properties 객체로 읽은 username: {}", myPropProperties.getUsername());
        log.info("[1-5] Properties 객체로 읽은 random port: {}", myPropProperties.getPort());

        // 1-6) 현재 활성화된 프로파일에 따른 실행 모드 출력
        log.info("[1-6] 현재 실행 환경 모드: {}", myEnvironment.getMode());

        System.out.println("========== 과제 출력 종료 ==========");
    }
}