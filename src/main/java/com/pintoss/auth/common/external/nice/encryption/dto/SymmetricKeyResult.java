package com.pintoss.auth.common.external.nice.encryption.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SymmetricKeyResult {
    private String key;
    private String iv;
    private String hmacKey;

    public SymmetricKeyResult(String key, String iv, String hmacKey) {
        this.key = key;
        this.iv = iv;
        this.hmacKey = hmacKey;
    }
}
