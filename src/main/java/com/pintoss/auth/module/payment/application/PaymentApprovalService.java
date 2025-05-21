package com.pintoss.auth.module.payment.application;

public interface PaymentApprovalService {

    PaymentApprovalResponse approval(PaymentApprovalRequest request);
}
