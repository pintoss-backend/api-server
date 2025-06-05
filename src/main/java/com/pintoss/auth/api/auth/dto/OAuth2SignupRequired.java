package com.pintoss.auth.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuth2SignupRequired implements OAuth2Response {
    private String email;
}
