package com.pintoss.auth.core.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.SecureRandom;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResetPasswordTest {

    private static final Set<Character> UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars().mapToObj(c -> (char) c).collect(java.util.stream.Collectors.toSet());
    private static final Set<Character> LOWER = "abcdefghijklmnopqrstuvwxyz".chars().mapToObj(c -> (char) c).collect(java.util.stream.Collectors.toSet());
    private static final Set<Character> DIGITS = "0123456789".chars().mapToObj(c -> (char) c).collect(java.util.stream.Collectors.toSet());
    private static final Set<Character> SYMBOLS = "!@#$%^&*".chars().mapToObj(c -> (char) c).collect(java.util.stream.Collectors.toSet());

    @Mock
    SecureRandom secureRandom;

    @Test
    @DisplayName("생성된 비밀번호는 정확히 10자리여야 한다.")
    void givenResetPassword_whenCreate_thenPasswordLengthIs10() {
        // Given
        when(secureRandom.nextInt(anyInt())).thenReturn(0);

        // When
        ResetPassword resetPassword = new ResetPassword(secureRandom);
        String password = resetPassword.getResetPassword();

        // Then
        assertThat(password).isNotNull().hasSize(10);
    }

    @RepeatedTest(10)
    @DisplayName("생성된 비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함해야 한다.")
    void givenResetPassword_whenCreate_thenContainsAllCharacterTypes() {
        // Given
        when(secureRandom.nextInt(anyInt())).thenReturn(0);

        // When
        String password = new ResetPassword(secureRandom).getResetPassword();

        // Then
        boolean hasUpper = password.chars().anyMatch(c -> UPPER.contains((char) c));
        boolean hasLower = password.chars().anyMatch(c -> LOWER.contains((char) c));
        boolean hasDigit = password.chars().anyMatch(c -> DIGITS.contains((char) c));
        boolean hasSymbol = password.chars().anyMatch(c -> SYMBOLS.contains((char) c));

        assertThat(hasUpper).isTrue();
        assertThat(hasLower).isTrue();
        assertThat(hasDigit).isTrue();
        assertThat(hasSymbol).isTrue();
    }
}
