package com.pintoss.auth.common.external.nice.encryption.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenerateHmacIntegrityCommand {

    private String hmacKey;
    private String encData;

    private GenerateHmacIntegrityCommand(String hmacKey, String encData) {
        this.hmacKey = hmacKey;
        this.encData = encData;
    }

    public static GenerateHmacIntegrityCommand create(String hmacKey, String encData) {
        return new GenerateHmacIntegrityCommand(hmacKey, encData);
    }
}
