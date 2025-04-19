package com.pintoss.auth.common.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.pintoss.auth.common.dto.ApiErrorResponse;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.common.exception.client.UnauthorizedException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Hidden
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public final ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorCode.INTERNAL_SERVER_ERROR,
            e.getMessage(),
            LocalDateTime.now()
        );

        log.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {BaseException.class})
    public final ResponseEntity<ApiErrorResponse> handleCustomException(BaseException e) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
            e.getHttpStatus(),
            e.getErrorCode(),
            e.getMessage(),
            LocalDateTime.now()
        );
        log.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }

    @ExceptionHandler(value ={BadRequestException.class})
    public final ResponseEntity<ApiErrorResponse> handleBadRequestException(BadRequestException e){
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
            e.getHttpStatus(),
            e.getErrorCode(),
            e.getMessage(),
            LocalDateTime.now()
        );
        log.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public final ResponseEntity<ApiErrorResponse> handleAuthorizationException(UnauthorizedException e){
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
            e.getHttpStatus(),
            e.getErrorCode(),
            e.getMessage(),
            LocalDateTime.now()
        );
        log.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("ExceptionHandler catch HandlerMethodValidationException : {}", e.getMessage());
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiErrorResponse errorResponse = ApiErrorResponse.withErrors(
            HttpStatus.BAD_REQUEST,
            ErrorCode.BAD_REQUEST,
            "유효하지 않은 요청입니다",
            errors
        );
        log.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public final ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(violation ->
            errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        ApiErrorResponse errorResponse = ApiErrorResponse.withErrors(
            HttpStatus.BAD_REQUEST,
            ErrorCode.BAD_REQUEST,
            e.getMessage(),
            errors
        );
        log.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public final ResponseEntity<ApiErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
            HttpStatus.BAD_REQUEST,
            ErrorCode.UNAUTHORIZED,
            e.getMessage(),
            LocalDateTime.now()
        );
        log.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {BadCredentialsException.class})
    public final ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
            HttpStatus.BAD_REQUEST,
            ErrorCode.UNAUTHORIZED,
            e.getMessage(),
            LocalDateTime.now()
        );
        log.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public final ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Throwable cause = e.getMostSpecificCause();

        String message;

        if(cause instanceof JsonParseException) {
            message = "요청 본문(JSON)이 올바르지 않습니다. 필드명과 값의 형식을 확인해주세요.";
        } else if (cause instanceof InvalidFormatException) {
            message = "요청 필드의 타입이 올바르지 않습니다.";
        } else {
            message = "요청 형식이 올바르지 않습니다.";
        }

        ApiErrorResponse errorResponse = ApiErrorResponse.of(
            HttpStatus.BAD_REQUEST,
            ErrorCode.BAD_REQUEST,
            message,
            LocalDateTime.now()
        );

        log.error(e.getMessage());
        e.printStackTrace();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
