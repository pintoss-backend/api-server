package com.pintoss.auth.core.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReissueCommand {

    private String refreshToken;

}
