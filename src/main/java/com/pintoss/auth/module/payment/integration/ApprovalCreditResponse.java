package com.pintoss.auth.module.payment.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApprovalCreditResponse {
    private String authNumber;            // 1004
    private String quota;                 // 0031
    private String cardCompanyCode;       // 0034
    private String pinNumber;             // 0008
    private String partCancelUse;         // 5312
    private String taxAmount;             // 5304
    private String taxFreeAmount;         // 5305
    private String simpleAffName;         // 5315
    private String simpleDealType;        // 5321
    private String cashReceiptAuthNumber; // 5337
}
