package com.pintoss.auth.support.exception;

import lombok.Getter;

@Getter
public class InvalidPhoneFormatException extends BadRequestException {
    public InvalidPhoneFormatException(String message){
        super(message);
    }

    public InvalidPhoneFormatException(ErrorCode errorCode, String message){
        super(errorCode, message);
    }
}
