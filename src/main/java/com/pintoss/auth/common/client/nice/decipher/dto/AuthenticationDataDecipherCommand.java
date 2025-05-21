package com.pintoss.auth.common.client.nice.decipher.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationDataDecipherCommand {

    private String key;
    private String iv;
    private String encData;

    private AuthenticationDataDecipherCommand(String key, String iv, String encData) {
        this.key = key;
        this.iv = iv;
        this.encData = encData;
    }

    public static AuthenticationDataDecipherCommand create(String key, String iv, String encData) {
        return new AuthenticationDataDecipherCommand(key, iv, encData);
    }
}
