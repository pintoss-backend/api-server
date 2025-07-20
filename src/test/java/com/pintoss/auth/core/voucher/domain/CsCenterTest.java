package com.pintoss.auth.core.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    @DisplayName("빈 전화번호 문자열이면 예외가 발생한다")
    void givenBlankTel_whenCreateCsCenter_thenThrowException() {
        // given
        String tel = "   ";

        // when & then
        assertThatThrownBy(() -> new CsCenter(tel))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객센터 전화번호는 필수 입력 값 입니다.");
    }

    @Test
    @DisplayName("잘못된 전화번호 형식이면 예외가 발생한다")
    void givenInvalidTelFormat_whenCreateCsCenter_thenThrowException() {
        // given
        String tel = "123-4567";

        // when & then
        assertThatThrownBy(() -> new CsCenter(tel))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효한 전화번호 형식이 아닙니다. (xxxx-xxxx, xxx-xxxx-xxxx 또는 xxx-xxx-xxxx 형식이어야 합니다.)");
    }

    @Test
    @DisplayName("xxxx-xxxx 형식의 전화번호는 유효하다")
    void givenValidLandlinePhoneFormat_whenCreateCsCenter_thenSuccess() {
        // given
        String tel = "1234-5678";

        // when & then
        CsCenter csCenter = new CsCenter(tel);
        assertThat(csCenter.getTel()).isEqualTo(tel);
    }

    @Test
    @DisplayName("xxx-xxxx-xxxx 형식의 전화번호는 유효하다")
    void givenValidMobilePhoneFormat_whenCreateCsCenter_thenSuccess() {
        // given
        String tel = "010-1234-5678";

        // when & then
        CsCenter csCenter = new CsCenter(tel);
        assertThat(csCenter.getTel()).isEqualTo(tel);
    }

    @Test
    @DisplayName("xxx-xxx-xxxx 형식의 전화번호는 유효하다")
    void givenValidShortMobilePhoneFormat_whenCreateCsCenter_thenSuccess() {
        // given
        String tel = "010-123-4567";

        // when & then
        CsCenter csCenter = new CsCenter(tel);
        assertThat(csCenter.getTel()).isEqualTo(tel);
    }
}
