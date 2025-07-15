package com.pintoss.auth.core.exception;

public class CoreBadRequestException extends RuntimeException {

    private final CoreErrorCode errorCode;

    public CoreBadRequestException(CoreErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CoreBadRequestException(CoreErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CoreErrorCode getErrorCode() {
        return errorCode;
    }
}
