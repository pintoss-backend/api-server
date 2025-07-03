package com.pintoss.auth.core.user.domain;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
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
