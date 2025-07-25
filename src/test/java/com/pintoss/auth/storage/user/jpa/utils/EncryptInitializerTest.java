package com.pintoss.auth.storage.user.jpa.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class EncryptInitializerTest {

    @Autowired
    EncryptProperties encryptProperties;

    @Test
    @DisplayName("애플리케이션이 실행될 때 환경변수가 초기화되어야 한다")
    void givenApplication_whenStart_thenEncryptUtilShouldBeInitialized() {
        // given
        String secretKey = encryptProperties.getSecretKey();
        String algorithm = encryptProperties.getAlgorithm();

        // when
        EncryptUtil.init(secretKey, algorithm);

        // then
        assertThat(encryptProperties.getAlgorithm()).isNotBlank();
        assertThat(encryptProperties.getSecretKey()).isNotBlank();
    }
}
