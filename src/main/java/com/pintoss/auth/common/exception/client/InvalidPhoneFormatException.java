package com.pintoss.auth.common.exception.client;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
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
