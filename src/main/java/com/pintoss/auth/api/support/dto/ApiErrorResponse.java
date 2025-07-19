package com.pintoss.auth.api.support.dto;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorResponse {

    private final String code;
    private final HttpStatus status;
    private final String errorCodeMessage;
    private final String errorMessage;
    private final LocalDateTime timestamp;
    private final Map<String, String> errors;

    @Builder
    private ApiErrorResponse(HttpStatus status, String errorCode, String errorMessage, LocalDateTime timestamp, Map<String, String> errors) {
        this.code = errorCode;
        this.status = status;
        this.errorCodeMessage = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public static ApiErrorResponse of(HttpStatus status, String errorCode, String errorMessage, LocalDateTime timestamp) {
        return ApiErrorResponse.builder()
            .status(status)
            .errorCode(errorCode)
            .errorMessage(errorMessage)
            .timestamp(timestamp)
            .build();
    }

    public static ApiErrorResponse withErrors(HttpStatus status, String errorCode, String errorMessage, Map<String, String> errors) {
        return ApiErrorResponse.builder()
            .status(status)
            .errorCode(errorCode)
            .errorMessage(errorMessage)
            .timestamp(LocalDateTime.now())
            .errors(errors)
            .build();
    }
}
