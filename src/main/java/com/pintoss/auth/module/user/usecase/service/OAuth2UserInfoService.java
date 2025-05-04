package com.pintoss.auth.module.user.usecase.service;

import com.pintoss.auth.module.user.usecase.dto.OAuth2UserInfoResponse;

public interface OAuth2UserInfoService {

    OAuth2UserInfoResponse getUserInfo(String code);

}
