package com.pintoss.auth.core.user.application;

import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.BadRequestException;
import com.pintoss.auth.api.support.security.jwt.JwtProvider;
import com.pintoss.auth.api.auth.dto.OAuth2FailResponse;
import com.pintoss.auth.api.auth.dto.OAuth2LoginSuccess;
import com.pintoss.auth.api.auth.dto.OAuth2Response;
import com.pintoss.auth.api.auth.dto.OAuth2SignupRequired;
import com.pintoss.auth.storage.user.jpa.entity.LoginType;
import com.pintoss.auth.storage.user.jpa.entity.User;
import com.pintoss.auth.core.user.application.dto.OAuth2UserInfoResponse;
import com.pintoss.auth.core.user.application.flow.policy.OAuth2UserInfoService;
import com.pintoss.auth.core.user.application.flow.policy.OAuth2UserInfoStrategy;
import com.pintoss.auth.core.user.application.flow.reader.UserReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuth2UseCase {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2UserInfoStrategy oAuth2UserInfoStrategy;
    private final UserReader userReader;
    private final JwtProvider jwtProvider;

    @Transactional
    public OAuth2Response oauthLogin(LoginType loginType, String code) {
        OAuth2UserInfoService userInfoService = oAuth2UserInfoStrategy.getOAuth2UserInfoService(loginType);
        OAuth2UserInfoResponse userInfo = userInfoService.getUserInfo(code);

        try {
            User user = userReader.readByEmail(userInfo.getEmail());
            user.validateSameLoginType(loginType);
            String accessToken = jwtProvider.createAccessToken(user);
            String refreshToken = jwtProvider.createRefreshToken(user);
            user.storeRefreshToken(refreshToken);
            return new OAuth2LoginSuccess(accessToken);
        } catch (BadRequestException e) {
            if(e.getErrorCode() == ErrorCode.USER_NOT_FOUND) {
                return new OAuth2SignupRequired(userInfo.getEmail());
            }
            return new OAuth2FailResponse();
        }
    }

    public String getOAuth2LoginUrl(LoginType loginType) {
        String registrationId = loginType.name().toLowerCase();
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(registrationId);
        if (clientRegistration == null) {
            throw new BadRequestException(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
        }
        // OAuth2 요청 파라미터 구성
        String authorizationUri = clientRegistration.getProviderDetails().getAuthorizationUri();
        String clientId = clientRegistration.getClientId();
        String redirectUri = clientRegistration.getRedirectUri();
        String scope = String.join(" ", clientRegistration.getScopes());
        String responseType = "code"; // OAuth2 인증 코드 요청

        // URL 인코딩 적용
        String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        String encodedScope = URLEncoder.encode(scope, StandardCharsets.UTF_8);
        // 최종 OAuth2 로그인 URL 생성
        return String.format("%s?client_id=%s&redirect_uri=%s&scope=%s&response_type=%s",
            authorizationUri, clientId, encodedRedirectUri, encodedScope, responseType);
    }

}
