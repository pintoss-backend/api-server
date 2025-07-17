package com.pintoss.auth.api.support.exception.handler;

import com.pintoss.auth.api.support.dto.ApiErrorResponse;
import com.pintoss.auth.api.support.util.DateTimeUtils;
import com.pintoss.auth.core.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class CoreExceptionHandler {

    @ExceptionHandler({CoreException.class})
    public final ResponseEntity<ApiErrorResponse> handleCoreExceptions(CoreException e, HttpServletRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();
        HttpStatus status = resolveHttpStatus(e.getErrorCode().getHttpErrorType());
        ApiErrorResponse errorResponse = ApiErrorResponse.of(status, e.getErrorCode().getCode(), e.getErrorCode().getMessage(), timestamp);
        log.error("[CoreException] errorCode={}, message={}, path={}, method={}, time={}",
                e.getErrorCode(), e.getMessage(), request.getRequestURI(), request.getMethod(), DateTimeUtils.formatKorean(timestamp));
        return new ResponseEntity<>(errorResponse, status);
    }

    private HttpStatus resolveHttpStatus(HttpErrorType errorType) {
        switch (errorType) {
            case BAD_REQUEST:
                return HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED:
                return HttpStatus.UNAUTHORIZED;
            case FORBIDDEN:
                return HttpStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case CONFLICT:
                return HttpStatus.CONFLICT;
            case INTERNAL_ERROR:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR; // Default fallback
        }
    }
}
