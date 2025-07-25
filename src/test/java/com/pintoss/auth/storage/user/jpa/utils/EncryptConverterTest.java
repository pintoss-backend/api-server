package com.pintoss.auth.storage.user.jpa.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class EncryptConverterTest {

    private static final String SECRET_KEY = "1234567890123456"; // 16 bytes
    private static final String ALGORITHM = "AES";

    private EncryptConverter converter;

    @BeforeEach
    void setUp() {
        // EncryptUtil 초기화 (전역 상태 설정)
        EncryptUtil.init(SECRET_KEY, ALGORITHM);
        converter = new EncryptConverter();
    }

    @Test
    @DisplayName("평문이 주어지면 암호화된 문자열로 변환한다.")
    void givenPlainText_whenConvertToDatabaseColumn_thenEncryptedText() {
        // given
        String plainText = "hello-world";

        // when
        String encryptedText = converter.convertToDatabaseColumn(plainText);

        // then
        assertThat(encryptedText).isNotBlank();
        assertThat(encryptedText).isNotEqualTo(plainText);
    }

    @Test
    @DisplayName("암호화된 문자이 복호화되면 원본 평문과 일치한다.")
    void givenEncryptedText_whenConvertToEntityAttribute_thenPlainText() {
        // given
        String plainText = "hello-world";
        String encryptedText = converter.convertToDatabaseColumn(plainText);

        // when
        String decryptedText = converter.convertToEntityAttribute(encryptedText);

        // then
        assertThat(decryptedText).isEqualTo(plainText);
    }

    @Test
    @DisplayName("null을 암호화하면 null이 된다.")
    void givenNull_whenConvertToDatabaseColumn_thenNull() {
        // when
        String result = converter.convertToDatabaseColumn(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("null을 복호화하면 null이 된다.")
    void givenNull_whenConvertToEntityAttribute_thenNull() {
        // when
        String result = converter.convertToEntityAttribute(null);

        // then
        assertThat(result).isNull();
    }
}
