package com.pintoss.auth.common.exception.client;

import com.pintoss.auth.common.exception.BaseException;
import com.pintoss.auth.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends BaseException {

    public NotFoundException(ErrorCode errorCode, String message){
        super(HttpStatus.BAD_REQUEST, errorCode, message);
    }

    public NotFoundException(ErrorCode errorCode){
        super(HttpStatus.BAD_REQUEST, errorCode, errorCode.getMessage());
    }
}
