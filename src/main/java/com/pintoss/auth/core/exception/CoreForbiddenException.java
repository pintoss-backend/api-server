package com.pintoss.auth.core.exception;

public class CoreForbiddenException extends RuntimeException {

    private final CoreErrorCode errorCode;

    public CoreForbiddenException(CoreErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CoreForbiddenException(CoreErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CoreErrorCode getErrorCode() {
        return errorCode;
    }
}
