package com.rookies5.myspringbootLab.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * ErrorObject 클래스
 * 에러 발생 시 클라이언트에게 전달할 메시지를 담음.
 */
@Getter
@Builder
public class ErrorObject {
    private final String message; // 에러 메시지
}