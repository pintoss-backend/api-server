package com.pintoss.auth.api.support.exception.handler;

import com.pintoss.auth.api.support.dto.ApiErrorResponse;
import com.pintoss.auth.api.support.util.DateTimeUtils;
import com.pintoss.auth.api.support.exception.client.BadRequestException;
import com.pintoss.auth.api.support.exception.client.UnauthorizedException;
import com.pintoss.auth.support.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class ClientExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public final ResponseEntity<ApiErrorResponse> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        return buildError(e, request);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public final ResponseEntity<ApiErrorResponse> handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request) {
        return buildError(e, request);
    }

    private ResponseEntity<ApiErrorResponse> buildError(BaseException e, HttpServletRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();
        ApiErrorResponse errorResponse = ApiErrorResponse.of(e.getHttpStatus(), e.getErrorCode().getCode(), e.getErrorCode().getMessage(), timestamp);
        log.error("[ClientException] errorCode={}, message={}, path={}, method={}, time={}",
                e.getErrorCode(), e.getMessage(), request.getRequestURI(), request.getMethod(), DateTimeUtils.formatKorean(timestamp));
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }
}
