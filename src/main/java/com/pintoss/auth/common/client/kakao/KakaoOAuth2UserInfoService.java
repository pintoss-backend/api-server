package com.pintoss.auth.common.client.kakao;

import com.pintoss.auth.core.user.application.dto.OAuth2UserInfoResponse;
import com.pintoss.auth.core.user.core.OAuth2UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("kakao")
@RequiredArgsConstructor
public class KakaoOAuth2UserInfoService implements OAuth2UserInfoService {

    private final KakaoOAuth2Client kakaoOAuth2Client;

    public OAuth2UserInfoResponse getUserInfo(String code) {
        KakaoTokenResponse tokenResponse = kakaoOAuth2Client.getAccessToken(code);
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoOAuth2Client.getUserInfo(tokenResponse.getAccessToken());

        return OAuth2UserInfoResponse.from(kakaoUserInfoResponse);
    }
}
