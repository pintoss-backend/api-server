package com.pintoss.auth.support.exception;

import lombok.Getter;

@Getter
public class EmptyPhoneException extends BadRequestException {
    public EmptyPhoneException(String message){
        super(message);
    }

    public EmptyPhoneException(ErrorCode errorCode, String message){
        super(errorCode, message);
    }
}
