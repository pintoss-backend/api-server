package com.pintoss.auth.core.user.domain.vo;

public class EncodedPassword {

    private final String value;

    public EncodedPassword(String value) {
        if (!value.startsWith("{bcrypt}")) {
            throw new IllegalArgumentException("비밀번호는 Bcrypt로 인코딩되어야 합니다");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
