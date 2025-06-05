package com.pintoss.auth.module.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReissueCommand {

    private String refreshToken;

}
