package com.pintoss.auth.core.user.application.dto;

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
