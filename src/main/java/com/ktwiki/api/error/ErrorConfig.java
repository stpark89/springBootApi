package com.ktwiki.api.error;

import com.ktwiki.api.dto.ApiResponse;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

/**
 * 예외 처리 담당 config
 * axios, ajax 호출시 에러 발생하면 호출되는 config
 */
@Log
@ControllerAdvice(annotations = RestController.class)
public class ErrorConfig {

    // 400
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> BadRequestException(final RuntimeException ex) {
        log.info("400 에러 ");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ex));
    }

    // 401
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        log.info("401 Error - 오소리티즈");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(ex));
    }

    // 404
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleNotFoundException(final NoHandlerFoundException ex) {
        log.info("404 Error - 페이지 없음 ");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ex));
    }

    // 500
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex) {
        log.info("500 에러 " +ex.getClass().getName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(ex));
    }

}
