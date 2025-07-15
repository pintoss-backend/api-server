package com.pintoss.auth.core.exception;

public class CoreConflictException extends RuntimeException {

    private final CoreErrorCode errorCode;

    public CoreConflictException(CoreErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CoreConflictException(CoreErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CoreErrorCode getErrorCode() {
        return errorCode;
    }

}
