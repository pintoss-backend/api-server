package com.pintoss.auth.common.client.billgate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "galaxia")
public class GalaxiaApiProperties {
    private Secret secret;
    private Server server;

    @Getter
    @Setter
    public static class Secret {
        private String key;
        private String iv;
    }

    @Getter
    @Setter
    public static class Server {
        private String host;
        private int port;
        private int timeOut;
    }
}
