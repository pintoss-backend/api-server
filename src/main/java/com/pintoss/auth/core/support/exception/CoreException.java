package com.pintoss.auth.core.support.exception;

public class CoreException extends RuntimeException {

    public CoreErrorCode errorCode;

    public CoreException(CoreErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CoreErrorCode getErrorCode() {
        return errorCode;
    }
}
