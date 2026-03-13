package com.rookies5.myspringbootLab;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 1-6) ProdConfig 클래스와 DevConfig 클래스 작성하기
 */


// 환경 정보를 저장할 데이터 클래스
@Getter
@Builder // 가이드에 명시된 @Getter, @Builder 어노테이션 사용
class MyEnvironment {
    private String mode;
}

// 운영 환경(prod)을 위한 설정 클래스
@Configuration
class ProdConfig {

    @Bean // 스프링 컨테이너에 빈으로 등록
    @Profile("prod") // 실행 시 'prod' 프로파일일 때만 이 빈을 생성함
    public MyEnvironment myEnvironment() {
        // mode 값을 "운영환경"으로 설정하여 생성
        return MyEnvironment.builder().mode("운영환경").build();
    }
}

// 개발/테스트 환경(test)을 위한 설정 클래스 (과제 가이드의 TestConfig)
@Configuration
class TestConfig {

    @Bean
    @Profile("test") // 실행 시 'test' 프로파일일 때만 이 빈을 생성함
    public MyEnvironment myEnvironment() {
        // mode 값을 "개발환경"으로 설정하여 생성
        return MyEnvironment.builder().mode("개발환경").build();
    }
}