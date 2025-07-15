package com.pintoss.auth.core.exception;

public class CoreNotFoundException extends RuntimeException {

    private final CoreErrorCode errorCode;

    public CoreNotFoundException(CoreErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CoreNotFoundException(CoreErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CoreErrorCode getErrorCode() {
        return errorCode;
    }
}
