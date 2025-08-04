package com.pintoss.auth.core.user.domain;

import com.pintoss.auth.core.user.domain.vo.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordTest {

    @Test
    @DisplayName("생성된 비밀번호는 정확히 10자리여야 한다.")
    void givenResetPassword_whenCreate_thenPasswordLengthIs10() {
        // Given
        String password = "*".repeat(10);

        // When
        Password resetPassword = new Password(password);

        // Then
        assertThat(resetPassword.getValue()).hasSize(10);
    }

    @Test
    @DisplayName("비밀번호가 null이면 예외가 발생한다")
    void givenNullPassword_whenCreate_thenException() {
        // Given
        String password = null;

        // When & Then
        assertThatThrownBy(() -> new Password(password))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호는 10자리여야 합니다.");
    }

    @Test
    @DisplayName("비밀번호가 10자리가 아니면 예외가 발생한다")
    void givenShortPassword_whenCreate_thenException() {
        // Given
        String password = "*".repeat(9);

        // When & Then
        assertThatThrownBy(() -> new Password(password))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호는 10자리여야 합니다.");
    }

    @Test
    @DisplayName("비밀번호가 10자리가 넘으면 예외가 발생한다")
    void givenLongPassword_whenCreate_thenException() {
        // Given
        String password = "*".repeat(11);

        // When & Then
        assertThatThrownBy(() -> new Password(password))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호는 10자리여야 합니다.");
    }
}
