package com.pintoss.auth.module.user.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReissueCommand {

    private String accessToken;
    private String refreshToken;

}
