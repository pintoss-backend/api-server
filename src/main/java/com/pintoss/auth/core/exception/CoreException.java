package com.pintoss.auth.core.exception;

public class CoreException extends RuntimeException {

    public CoreErrorCode errorCode;

    public CoreException(CoreErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CoreErrorCode getErrorCode() {
        return errorCode;
    }
}
