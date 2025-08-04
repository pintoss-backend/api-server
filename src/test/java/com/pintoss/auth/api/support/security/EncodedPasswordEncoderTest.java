package com.pintoss.auth.api.support.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class EncodedPasswordEncoderTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("평문이 주어졌을 때 비밀번호가 정상적으로 암호화되어야 한다")
    void givenPlainText_whenEncode_thenEncoded() {
        // given
        String plainText = "password123";

        // when
        String encodedPassword = passwordEncoder.encode(plainText);

        // then
        assertThat(passwordEncoder.matches(plainText, encodedPassword)).isTrue();
        log.info("plainText: {}, encodedPassword: {}", plainText, encodedPassword);
    }

    @Test
    @DisplayName("평문은 bcrypt로 암호화되어야 한다")
    void givenPlainText_whenEncode_thenEncodedWithBcrypt() {
        // given
        String plainText = "password123";

        // when
        String encodedPassword = passwordEncoder.encode(plainText);

        // then
        assertThat(encodedPassword).startsWith("{bcrypt}");
        assertThat(passwordEncoder.matches(plainText, encodedPassword)).isTrue();
    }

    @Test
    @DisplayName("동일한 평문이라도 매번 인코딩 결과는 달라야 한다")
    void givenSamePlainText_whenEncodedMultipleTimes_thenEncodedResultsAreDifferent() {
        // given
        String plainText = "password123";

        // when
        String encodedPassword1 = passwordEncoder.encode(plainText);
        String encodedPassword2 = passwordEncoder.encode(plainText);

        // then
        assertThat(encodedPassword1).isNotEqualTo(encodedPassword2);
        assertThat(passwordEncoder.matches(plainText, encodedPassword1)).isTrue();
        assertThat(passwordEncoder.matches(plainText, encodedPassword2)).isTrue();
    }

}
