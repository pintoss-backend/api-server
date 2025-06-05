package com.pintoss.auth.module.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NiceVerificationResult {
    private Boolean isSuccess;
    private String name;
    private String tel;
    private String purpose;
}
