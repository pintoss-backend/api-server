package com.pintoss.auth.core.user.application.flow.processor;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EncryptProperties.class)
@RequiredArgsConstructor
public class EncryptInitializer {

    private final EncryptProperties encryptProperties;

    @PostConstruct
    public void init() {
        EncryptUtil.init(encryptProperties.getSecretKey(), encryptProperties.getAlgorithm());
    }
}
