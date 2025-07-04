package com.pintoss.auth.client.nice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties("nice-auth")
@Configuration
public class NiceApiProperties {
    private String clientId;
    private String clientSecret;
    private String accessTokenUri;
    private String encryptedTokenUri;
    private String redirectUri;
    private String productId;
}
