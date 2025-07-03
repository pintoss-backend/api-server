package com.pintoss.auth.core.user.core;

import com.pintoss.auth.core.user.application.dto.OAuth2UserInfoResponse;

public interface OAuth2UserInfoService {

    OAuth2UserInfoResponse getUserInfo(String code);

}
