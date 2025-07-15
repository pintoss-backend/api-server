package com.pintoss.auth.api.support.exception.client;

import com.pintoss.auth.support.exception.ErrorCode;
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
