package com.pintoss.auth.support.exception;


import com.pintoss.auth.api.common.response.ApiErrorResponse;
import com.pintoss.auth.api.common.util.DateTimeUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class SecurityExceptionHandler {

    @ExceptionHandler(value = {AuthorizationDeniedException.class})
    public final ResponseEntity<ApiErrorResponse> handleAccessDenied(AuthorizationDeniedException e, HttpServletRequest request) {
        return error(ErrorCode.AUTH_ACCESS_DENIED, HttpStatus.FORBIDDEN, e, request);
    }

    @ExceptionHandler(value = {AuthenticationException.class, UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(Exception e, HttpServletRequest request) {
        return error(ErrorCode.UNAUTHORIZED, HttpStatus.BAD_REQUEST, e, request);
    }

    private ResponseEntity<ApiErrorResponse> error(ErrorCode code, HttpStatus status, Exception e, HttpServletRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();
        ApiErrorResponse errorResponse = ApiErrorResponse.of(status, code.getCode(), code.getMessage(), timestamp);
        log.error("[SecurityException] errorCode={}, message={}, path={}, method={}, time={}",
                code,
                e.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                DateTimeUtils.formatKorean(timestamp));
        return new ResponseEntity<>(errorResponse, status);
    }
}
