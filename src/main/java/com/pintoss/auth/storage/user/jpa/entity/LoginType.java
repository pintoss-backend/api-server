package com.pintoss.auth.storage.user.jpa.entity;

import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.BadRequestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType {

    LOCAL("local"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String value;

    public static LoginType fromString(String value) {
        for (LoginType loginType : LoginType.values()) {
            if(loginType.value.equalsIgnoreCase(value)) {
                return loginType;
            }
        }
        throw new BadRequestException(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
    }
}
