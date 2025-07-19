package com.pintoss.auth.core.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsCenterTest {

    @Test
    @DisplayName("전화번호가 없으면 예외가 발생한다")
    void givenNullOrBlankTel_whenCreateCsCenter_thenThrowException() {
        // given
        String tel = null;

        // when & then
        assertThatThrownBy(() -> new CsCenter(tel))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객센터 전화번호는 필수 입력 값 입니다.");
    }
}
