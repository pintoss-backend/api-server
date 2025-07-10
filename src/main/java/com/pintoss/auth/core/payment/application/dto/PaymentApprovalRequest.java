package com.pintoss.auth.core.payment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentApprovalRequest {

    private String serviceCode;
    private String orderNo;
    private String message;

}
