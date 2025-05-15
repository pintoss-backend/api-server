package com.pintoss.auth.module.user.infra.mail;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gmail")
public class GmailApiProperties {

    private String applicationName;
    private String userEmail;
    private List<String> serviceAccountKeys;
    private List<String> oauthScopes;

}
