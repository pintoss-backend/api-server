package com.pintoss.auth.core.exception;

public class CoreInternalException extends RuntimeException {

    private final CoreErrorCode errorCode;

    public CoreInternalException(CoreErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CoreInternalException(CoreErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CoreErrorCode getErrorCode() {
        return errorCode;
    }
}
