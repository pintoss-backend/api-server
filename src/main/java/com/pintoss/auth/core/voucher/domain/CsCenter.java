package com.pintoss.auth.core.voucher.domain;

import lombok.Getter;

@Getter
public class CsCenter {

    private static final String LANDLINE_PHONE_REGEX = "^\\d{4}-\\d{4}$";
    private static final String MOBILE_PHONE_REGEX = "^\\d{3}-\\d{4}-\\d{4}$";
    private static final String MOBILE_PHONE_SHORT_REGEX = "^\\d{3}-\\d{3}-\\d{4}$";
    
    private String tel;

    public CsCenter(String tel) {
        validate(tel);
        this.tel = tel;
    }

    private void validate(String tel){
        // TODO : 유효성 검증
        // Case1 . xxxx-xxxx
        // Case2 . xxx-xxxx-xxxx
        // Case3 . 빈값, null
        // 이외에 IllegalArgumentException을 던진다.
        if (tel == null || tel.isBlank()) {
            throw new IllegalArgumentException("고객센터 전화번호는 필수 입력 값 입니다.");
        }

        boolean isValidFormat = tel.matches(LANDLINE_PHONE_REGEX)
                || tel.matches(MOBILE_PHONE_REGEX)
                || tel.matches(MOBILE_PHONE_SHORT_REGEX);

        if (!isValidFormat) {
            throw new IllegalArgumentException("유효한 전화번호 형식이 아닙니다. (xxxx-xxxx, xxx-xxxx-xxxx 또는 xxx-xxx-xxxx 형식이어야 합니다.)");
        }
    }
}
