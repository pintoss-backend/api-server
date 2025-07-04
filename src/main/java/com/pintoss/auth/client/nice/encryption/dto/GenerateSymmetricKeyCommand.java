package com.pintoss.auth.client.nice.encryption.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenerateSymmetricKeyCommand {
    private String reqDtim;
    private String reqNo;
    private String tokenValue;


    private GenerateSymmetricKeyCommand(String reqDtim, String reqNo, String tokenValue) {
        this.reqDtim = reqDtim;
        this.reqNo = reqNo;
        this.tokenValue = tokenValue;
    }

    public static GenerateSymmetricKeyCommand create(String reqDtim, String reqNo, String tokenValue) {
        return new GenerateSymmetricKeyCommand(reqDtim, reqNo, tokenValue);
    }
}
