package com.pintoss.auth.core.exception;

public class CoreUnauthorizedException extends RuntimeException {

    private final CoreErrorCode errorCode;

    public CoreUnauthorizedException(CoreErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CoreUnauthorizedException(CoreErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CoreErrorCode getErrorCode() {
        return errorCode;
    }
}
