package com.pintoss.auth.module.user.core;

import com.pintoss.auth.module.user.application.dto.OAuth2UserInfoResponse;

public interface OAuth2UserInfoService {

    OAuth2UserInfoResponse getUserInfo(String code);

}
