package com.pintoss.auth.client.nice.client;

import com.pintoss.auth.client.nice.client.dto.NiceApiCryptoTokenRequest;
import com.pintoss.auth.client.nice.client.dto.NiceApiAccessTokenResponse;
import com.pintoss.auth.client.nice.client.dto.NiceApiCryptoTokenResponse;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class NiceApiClient {

    private final WebClient webClient;

    public NiceApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
            .baseUrl("https://svc.niceapi.co.kr:22001")
            .build();
    }

    public Mono<NiceApiAccessTokenResponse> getAccessToken(String uri, String clientId, String clientSecret) {
        String authorizationHeader = "Basic " + Base64.getEncoder()
            .encodeToString((clientId + ":" + clientSecret).getBytes());System.out.println(authorizationHeader);
            log.debug("GetAccessToken API Authorization Header: {}", authorizationHeader);
        return webClient.post()
            .uri(uri)
            .header("Authorization", authorizationHeader)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .bodyValue("grant_type=client_credentials&scope=default")
            .retrieve()
            .bodyToMono(NiceApiAccessTokenResponse.class);
    }

    public Mono<NiceApiCryptoTokenResponse> getCryptoToken(String uri, String accessToken, String clientId, String productId, NiceApiCryptoTokenRequest niceApiCryptoTokenRequest) {
        String authorizationHeader = "bearer " + Base64.getEncoder()
            .encodeToString((String.valueOf(accessToken+":"+ (new Date().getTime() / 1000)+":"+clientId)).getBytes());
        log.debug("GetCryptoToken API Authorization Header: {}", authorizationHeader);
        return webClient.post()
            .uri(uri)
            .header("Authorization", authorizationHeader)
            .header("ProductID", productId)
            .header("Content-Type", "application/json")
            .body(Mono.just(niceApiCryptoTokenRequest), NiceApiCryptoTokenRequest.class)
            .retrieve()
            .bodyToMono(NiceApiCryptoTokenResponse.class);
    }
}
