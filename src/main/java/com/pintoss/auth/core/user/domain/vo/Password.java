package com.pintoss.auth.core.user.domain.vo;


public class Password {

    private final String value;

    public Password(String value) {
        if (value == null || value.length() != 10) {
            throw new IllegalArgumentException("비밀번호는 10자리여야 합니다.");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
