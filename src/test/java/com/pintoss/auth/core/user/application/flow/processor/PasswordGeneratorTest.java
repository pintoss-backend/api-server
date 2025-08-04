package com.pintoss.auth.core.user.application.flow.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.security.SecureRandom;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PasswordGeneratorTest {

    private static final Set<Character> UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars().mapToObj(c -> (char) c).collect(java.util.stream.Collectors.toSet());
    private static final Set<Character> LOWER = "abcdefghijklmnopqrstuvwxyz".chars().mapToObj(c -> (char) c).collect(java.util.stream.Collectors.toSet());
    private static final Set<Character> DIGITS = "0123456789".chars().mapToObj(c -> (char) c).collect(java.util.stream.Collectors.toSet());
    private static final Set<Character> SYMBOLS = "!@#$%^&*".chars().mapToObj(c -> (char) c).collect(java.util.stream.Collectors.toSet());


    @Autowired
    SecureRandom secureRandom;

    @Autowired
    PasswordGenerator generator;

    @RepeatedTest(10)
    @DisplayName("생성된 비밀번호에는 모든 문자셋이 포함되어야 한다")
    void givenPasswordGenerator_whenGenerate_thenContainsAllCharSets() {
        // Given
        String password = generator.generate();

        // When & Then
        assertThat(password.chars().mapToObj(c -> (char) c)).containsAnyOf(UPPER.toArray(new Character[0]));
        assertThat(password.chars().mapToObj(c -> (char) c)).containsAnyOf(LOWER.toArray(new Character[0]));
        assertThat(password.chars().mapToObj(c -> (char) c)).containsAnyOf(DIGITS.toArray(new Character[0]));
        assertThat(password.chars().mapToObj(c -> (char) c)).containsAnyOf(SYMBOLS.toArray(new Character[0]));
    }

    @Test
    @DisplayName("생성된 비밀번호는 정확히 10자리여야 한다")
    void givenPasswordGenerator_whenGenerate_thenPasswordLengthIs10() {
        // Given
        String password = generator.generate();

        // When & Then
        assertThat(password).hasSize(10);
    }

    @RepeatedTest(10)
    @DisplayName("생성되는 비밀번호는 항상 달라야 한다")
    void givenPasswordGenerator_whenGenerateMultipleTimes_thenDifferentPasswords() {
        // Given
        String password1 = generator.generate();
        String password2 = generator.generate();

        // When & Then
        assertThat(password1).isNotEqualTo(password2);
    }
}
