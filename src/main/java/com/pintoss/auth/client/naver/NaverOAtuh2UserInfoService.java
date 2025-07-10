package com.pintoss.auth.client.naver;

import com.pintoss.auth.core.user.application.dto.OAuth2UserInfoResponse;
import com.pintoss.auth.core.user.application.flow.policy.OAuth2UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("naver")
@RequiredArgsConstructor
public class NaverOAtuh2UserInfoService implements OAuth2UserInfoService {

    private final NaverOAuth2Client naverOAuth2Client;

    public OAuth2UserInfoResponse getUserInfo(String code) {
        String accessToken = naverOAuth2Client.getAccessToken(code);
        NaverUserInfoResponse naverUserInfoResponse = naverOAuth2Client.getUserInfo(accessToken);

        return OAuth2UserInfoResponse.from(naverUserInfoResponse);
    }
}
