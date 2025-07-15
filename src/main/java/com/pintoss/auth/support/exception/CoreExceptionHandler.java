package com.pintoss.auth.support.exception;

import com.pintoss.auth.api.common.response.ApiErrorResponse;
import com.pintoss.auth.api.common.util.DateTimeUtils;
import com.pintoss.auth.core.exception.CoreBadRequestException;
import com.pintoss.auth.core.exception.CoreConflictException;
import com.pintoss.auth.core.exception.CoreNotFoundException;
import com.pintoss.auth.core.exception.CoreUnsupportedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class CoreExceptionHandler {

    @ExceptionHandler({CoreBadRequestException.class, CoreConflictException.class, CoreNotFoundException.class, CoreUnsupportedException.class})
    public final ResponseEntity<ApiErrorResponse> handleCoreExceptions(BaseException e, HttpServletRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();
        HttpStatus status = e.getHttpStatus();
        ApiErrorResponse errorResponse = ApiErrorResponse.of(status, e.getErrorCode().getCode(), e.getErrorCode().getMessage(), timestamp);
        log.error("[CoreException] errorCode={}, message={}, path={}, method={}, time={}",
                e.getErrorCode(), e.getMessage(), request.getRequestURI(), request.getMethod(), DateTimeUtils.formatKorean(timestamp));
        return new ResponseEntity<>(errorResponse, status);
    }
}
