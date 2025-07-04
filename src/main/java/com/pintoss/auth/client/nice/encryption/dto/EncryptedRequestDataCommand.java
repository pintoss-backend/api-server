package com.pintoss.auth.client.nice.encryption.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EncryptedRequestDataCommand {

    private String key;
    private String iv;
    private String reqNo;
    private String redirectUrl;
    private String siteCode;
    private String purpose;

    private EncryptedRequestDataCommand(String key, String iv, String reqNo, String redirectUrl, String siteCode, String purpose) {
        this.key = key;
        this.iv = iv;
        this.reqNo = reqNo;
        this.redirectUrl = redirectUrl;
        this.siteCode = siteCode;
        this.purpose = purpose;
    }

    public static EncryptedRequestDataCommand create(String key, String iv, String reqNo, String redirectUrl, String siteCode, String purpose) {
        return new EncryptedRequestDataCommand(key, iv, reqNo, redirectUrl, siteCode, purpose);
    }
}
