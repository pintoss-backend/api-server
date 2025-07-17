package com.pintoss.auth.support.exception;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class BaseException extends RuntimeException {
    private HttpStatus httpStatus;
    private ErrorCode errorCode;
    private LocalDateTime timeStamp;

    public BaseException(HttpStatus httpStatus, ErrorCode errorCode, String message){
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.timeStamp = LocalDateTime.now();
    }
}
