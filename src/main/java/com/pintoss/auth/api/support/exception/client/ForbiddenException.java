package com.pintoss.auth.api.support.exception.client;

import com.pintoss.auth.support.exception.BaseException;
import com.pintoss.auth.support.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {

    public ForbiddenException(String message){
        super(HttpStatus.FORBIDDEN, ErrorCode.AUTH_ACCESS_DENIED, message);
    }

    public ForbiddenException(ErrorCode errorCode, String message){
        super(HttpStatus.FORBIDDEN, errorCode, message);
    }

    public ForbiddenException(ErrorCode errorCode){
        super(HttpStatus.FORBIDDEN, errorCode, errorCode.getMessage());
    }

}
