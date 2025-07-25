package com.pintoss.auth.core.user.application.flow.processor;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties(prefix = "spring.security.encrypt")
public class EncryptProperties {
    private final String secretKey;
    private final String algorithm;

    public EncryptProperties(String secretKey, String algorithm) {
        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalArgumentException("암호화 알고리즘 키는 필수입니다");
        }
        if (algorithm == null || algorithm.isBlank()) {
            throw new IllegalArgumentException("알고리즘은 필수입니다.");
        }
        this.secretKey = secretKey;
        this.algorithm = algorithm;
    }
}
