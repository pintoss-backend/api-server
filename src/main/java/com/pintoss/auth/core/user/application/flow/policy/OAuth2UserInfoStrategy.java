package com.pintoss.auth.core.user.application.flow.policy;

import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.client.BadRequestException;
import com.pintoss.auth.core.user.domain.LoginType;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2UserInfoStrategy {

    private final Map<String, OAuth2UserInfoService> oAuth2UserInfoServiceMap;

    public OAuth2UserInfoService getOAuth2UserInfoService(LoginType providerType) {
        OAuth2UserInfoService service = oAuth2UserInfoServiceMap.get(providerType.getValue());

        if(service == null) {
            throw new BadRequestException(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
        }

        return service;
    }

}
