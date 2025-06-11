package com.pintoss.auth.common.client.billgate;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "galaxia")
public class GalaxiaApiProperties {

    private final Secret secret;
    private final Server server;

    public GalaxiaApiProperties(Secret secret, Server server) {
        this.secret = secret;
        this.server = server;
    }

    @Data
    public static class Secret {
        private String key;
        private String iv;
    }

    @Data
    public static class Server {
        private String host;
        private int port;
        private int timeOut;
    }
}
