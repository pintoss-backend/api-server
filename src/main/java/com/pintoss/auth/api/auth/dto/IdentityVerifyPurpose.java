package com.pintoss.auth.api.auth.dto;

import lombok.Getter;

@Getter
public enum IdentityVerifyPurpose {
    SIGNUP("signup"),
    PASSWORD_RESET("password_reset");

    private final String code;

    IdentityVerifyPurpose(String code) {
        this.code = code;
    }

    public static IdentityVerifyPurpose fromCode(String code) {
        for (IdentityVerifyPurpose purpose : values()) {
            if (purpose.getCode().equals(code)) {
                return purpose;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
