package com.pintoss.auth.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED, message);
    }
    public UnauthorizedException(ErrorCode errorCode, String message){
        super(HttpStatus.BAD_REQUEST, errorCode, message);
    }
}
