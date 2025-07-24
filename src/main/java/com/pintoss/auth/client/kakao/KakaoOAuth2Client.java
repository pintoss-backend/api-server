package com.pintoss.auth.client.kakao;

import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.BadRequestException;
import com.pintoss.auth.core.user.domain.LoginType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoOAuth2Client {

    private final WebClient webClient;

    private final ClientRegistrationRepository clientRegistrationRepository;

    public KakaoOAuth2Client(WebClient.Builder webClientBuilder,
                             ClientRegistrationRepository clientRegistrationRepository) {
        this.webClient = webClientBuilder
            .baseUrl("https://kauth.kakao.com")
            .build();
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    public KakaoTokenResponse getAccessToken(String code) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(
            LoginType.KAKAO.getValue());
        if(clientRegistration == null) {
            throw new BadRequestException(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
        }

        return this.webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/oauth/token")
                .queryParam("client_id", clientRegistration.getClientId())
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_secret", clientRegistration.getClientSecret())
                .queryParam("redirect_uri", clientRegistration.getRedirectUri())
                .queryParam("code", code)
                .build())
            .retrieve()
            .bodyToMono(KakaoTokenResponse.class)
            .block();
    }

    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(LoginType.KAKAO.getValue());
        if(clientRegistration == null) {
            throw new BadRequestException(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
        }

        return this.webClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer "+ accessToken)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .retrieve()
            .bodyToMono(KakaoUserInfoResponse.class)
            .block();
    }

}
