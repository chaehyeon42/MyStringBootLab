package com.rookies5.myspringbootLab.exception.advice;

// 같은 exception 패키지 안에 있더라도 하위 패키지(advice)이므로 import가 필요.
import com.rookies5.myspringbootLab.exception.BusinessException;
import com.rookies5.myspringbootLab.exception.ErrorObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  전역 예외 처리기
 *
 */
@RestControllerAdvice // 스프링이 이 클래스를 에러 처리기로 인식하게 합니다.
public class DefaultExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorObject> handleBusinessException(BusinessException ex) {

        // 결과로 보여줄 ErrorObject 생성
        ErrorObject errorObject = ErrorObject.builder()
                .message(ex.getMessage())
                .build();

        //500이 아닌, 예외가 던진 상태코드(404 등)를 반환.
        return new ResponseEntity<>(errorObject, ex.getHttpStatus());
    }
}