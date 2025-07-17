package com.pintoss.auth.api.support.exception.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.pintoss.auth.api.support.dto.ApiErrorResponse;
import com.pintoss.auth.support.exception.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(1)
@Slf4j
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ApiErrorResponse errorResponse = ApiErrorResponse.withErrors(
                HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.getCode(), "유효하지 않은 요청입니다", errors);
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public final ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(v -> errors.put(v.getPropertyPath().toString(), v.getMessage()));
        ApiErrorResponse errorResponse = ApiErrorResponse.withErrors(
                HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage(), errors);
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public final ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message;
        Throwable cause = e.getMostSpecificCause();
        if (cause instanceof JsonParseException) {
            message = "요청 본문(JSON)이 올바르지 않습니다.";
        } else if (cause instanceof InvalidFormatException) {
            message = "요청 필드의 타입이 올바르지 않습니다.";
        } else {
            message = "요청 형식이 올바르지 않습니다.";
        }
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.getCode(), message, LocalDateTime.now());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}