package com.pintoss.auth.support.exception.client;

import com.pintoss.auth.support.exception.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateEmailException extends BadRequestException {
    public DuplicateEmailException(String message){
        super(message);
    }

    public DuplicateEmailException(ErrorCode errorCode, String message){
        super(errorCode, message);
    }
}
