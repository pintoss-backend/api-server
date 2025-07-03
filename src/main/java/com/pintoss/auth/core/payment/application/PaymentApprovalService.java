package com.pintoss.auth.core.payment.application;

public interface PaymentApprovalService {

    PaymentApprovalResponse approval(PaymentApprovalRequest request);
}
