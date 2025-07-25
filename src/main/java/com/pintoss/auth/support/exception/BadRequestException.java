package com.pintoss.auth.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends BaseException {
    public BadRequestException(String message){
        super(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, message);
    }

    public BadRequestException(ErrorCode errorCode, String message){
        super(HttpStatus.BAD_REQUEST, errorCode, message);
    }

    public BadRequestException(ErrorCode errorCode){
        super(HttpStatus.BAD_REQUEST, errorCode, errorCode.getMessage());
    }

}
