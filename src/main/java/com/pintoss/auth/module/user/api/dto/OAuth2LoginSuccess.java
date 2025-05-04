package com.pintoss.auth.module.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuth2LoginSuccess implements OAuth2Response {
    private String accessToken;
}

