package com.pintoss.auth.core.exception;

public class CoreUnsupportedException extends RuntimeException {

    private final CoreErrorCode errorCode;

    public CoreUnsupportedException(CoreErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CoreUnsupportedException(CoreErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CoreErrorCode getErrorCode() {
        return errorCode;
    }
}
