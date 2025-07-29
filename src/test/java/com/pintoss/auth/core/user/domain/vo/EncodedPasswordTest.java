package com.pintoss.auth.core.user.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EncodedPasswordTest {

    @Test
    @DisplayName("비밀번호가 bcrypt로 인코딩되지 않았다면 예외가 발생한다")
    void givenNonBcryptEncodedPassword_whenCreate_thenException() {
        // Given
        String nonBcryptPassword = "password123";

        // When & Then
        assertThatThrownBy(() -> new EncodedPassword(nonBcryptPassword))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호는 Bcrypt로 인코딩되어야 합니다");
    }

    @Test
    @DisplayName("비밀번호가 bcrypt로 인코딩되었다면 정상적으로 생성된다")
    void givenBcryptEncodedPassword_whenCreate_thenSuccess() {
        // Given
        String bcryptPassword = "{bcrypt}$2a$10$EIX/4z5Z1j1Q8e5f9b6e0O0O0O0O0O0O0O0O0O0O0O0O0O0O";

        // When
        EncodedPassword encodedPassword = new EncodedPassword(bcryptPassword);

        // Then
        assertNotNull(encodedPassword);
        assertEquals(bcryptPassword, encodedPassword.getValue());
    }
}
