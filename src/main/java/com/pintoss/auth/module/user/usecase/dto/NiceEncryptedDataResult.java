package com.pintoss.auth.module.user.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NiceEncryptedDataResult {
    private String token_version_id;
    private String enc_data;
    private String integrity_value;
}
