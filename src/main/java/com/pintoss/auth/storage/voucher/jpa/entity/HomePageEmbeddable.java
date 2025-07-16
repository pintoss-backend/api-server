package com.pintoss.auth.storage.voucher.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HomePageEmbeddable {
    private String url;

    public HomePageEmbeddable(String url) {
        validate(url);
        this.url = url;
    }

    private void validate(String url) {
        // TODO : 유효성 검증
        // Case1 . url
        // Case2 . 빈값, null
        // 이외에 IllegalArgumentException을 던진다.
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException( "홈페이지 URL은 필수 입력 값입니다.");
        }
        if (!url.matches("^(http|https)://.*")) {
            throw new IllegalArgumentException("유효한 URL 형식이 아닙니다. (http:// 또는 https://로 시작해야 합니다.)");
        }
    }
}
