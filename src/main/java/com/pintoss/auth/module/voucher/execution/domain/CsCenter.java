package com.pintoss.auth.module.voucher.execution.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CsCenter {
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
        if (!tel.matches("^\\d{4}-\\d{4}$") && !tel.matches("^\\d{3}-\\d{4}-\\d{4}$")) {
            throw new IllegalArgumentException("유효한 전화번호 형식이 아닙니다. (xxxx-xxxx 또는 xxx-xxxx-xxxx 형식이어야 합니다.)");
        }
    }
}
