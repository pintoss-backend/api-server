package com.pintoss.auth.common.exception.client;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
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
