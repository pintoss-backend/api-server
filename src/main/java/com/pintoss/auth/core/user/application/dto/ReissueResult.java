package com.pintoss.auth.core.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReissueResult {

    private String accessToken;
    private String refreshToken;

}
