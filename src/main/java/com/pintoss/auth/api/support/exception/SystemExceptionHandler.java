package com.pintoss.auth.api.support.exception;

import com.pintoss.auth.api.support.dto.ApiErrorResponse;
import com.pintoss.auth.api.support.util.DateTimeUtils;
import com.pintoss.auth.support.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class SystemExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public final ResponseEntity<ApiErrorResponse> handleException(Exception e, HttpServletRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                timestamp
        );
        log.error("[INTERNAL_SERVER_ERROR] errorCode={}, exception={}, message={}, path={}, method={}, time={}",
                ErrorCode.INTERNAL_SERVER_ERROR,
                e.getClass().getSimpleName(),
                e.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                DateTimeUtils.formatKorean(timestamp));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
