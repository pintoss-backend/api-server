package com.pintoss.auth.api.support.exception.client;

import com.pintoss.auth.support.exception.ErrorCode;
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
