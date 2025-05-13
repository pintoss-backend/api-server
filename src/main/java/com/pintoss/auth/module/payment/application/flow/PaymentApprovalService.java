package com.pintoss.auth.module.payment.application.flow;

import com.pintoss.auth.module.payment.integration.PaymentApprovalResponse;

public interface PaymentApprovalService {

    PaymentApprovalResponse approval(String serviceCode, String orderNo, String message);
}
