package org.zerock.mallapi.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomControllerAdvice {

    // postman 을 사용하여 테스트. swagger 는 스프링부트 3.x 버전에서는 openapi 와 함께 사용해야함

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> notExist(NoSuchElementException e) {   // 리턴타입이 ResponseEntity 인 이유는 http 통신의 상태코드로 분기처리를 하기 좋기 때문

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notExist(MethodArgumentNotValidException e) {

        // NOT_ACCEPTABLE, BADREQUEST 등등 선택해서 사용하면 된다.
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("msg", e.getMessage()));
    }

}
