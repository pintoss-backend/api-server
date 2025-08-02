package com.pintoss.auth.storage.user.jpa.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EncryptUtilTest {

    private static final String SECRET_KEY = "1234567890123456"; // 16 bytes (128-bit)
    private static final String ALGORITHM = "AES";

    @BeforeEach
    void setUp() {
        EncryptUtil.init(SECRET_KEY, ALGORITHM);
    }

    @Test
    @DisplayName("문자열을 암호화하고 다시 복호화하면 원본과 일치한다.")
    void givenPlain_whenEncryptAndDecrypt_thenTextShouldBeEquals() {
        // given
        String plainText = "hello-world";

        // when
        String encrypted = EncryptUtil.encrypt(plainText);
        String decrypted = EncryptUtil.decrypt(encrypted);

        // then
        assertThat(decrypted).isEqualTo(plainText);
    }

    @Test
    @DisplayName("암호화된 문자열은 원본과 다르다.")
    void givenPlain_whenEncrypt_thenEncryptedTextShouldNotBeEquals() {
        // given
        String plainText = "hello-world";

        // when
        String encrypted = EncryptUtil.encrypt(plainText);

        // then
        assertThat(encrypted).isNotEqualTo(plainText);
    }

    @Test
    @DisplayName("다른 키로 복호화하면 예외가 발생한다.")
    void givenEncrypted_whenDecryptWithWrongKey_thenExceptionShouldBeThrown() {
        // given
        String plainText = "hello-world";
        String encrypted = EncryptUtil.encrypt(plainText);

        // when
        EncryptUtil.init("wrong-key-123456", ALGORITHM);

        // then
        assertThatThrownBy(() -> EncryptUtil.decrypt(encrypted))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("복호화 실패");
    }

    @Test
    @DisplayName("다른 알고리즘으로 복호화하면 예외가 발생한다.")
    void givenEncrypted_whenDecryptWithWrongAlgorithm_thenExceptionShouldBeThrown() {
        // given
        String plainText = "hello-world";
        String encrypted = EncryptUtil.encrypt(plainText);

        // when
        EncryptUtil.init(SECRET_KEY, "DES"); // 다른 알고리즘 사용

        // then
        assertThatThrownBy(() -> EncryptUtil.decrypt(encrypted))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("복호화 실패");
    }
}
