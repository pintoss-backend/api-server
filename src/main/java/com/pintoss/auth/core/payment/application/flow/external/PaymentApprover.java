package com.pintoss.auth.core.payment.application.flow.external;

import com.pintoss.auth.core.payment.application.dto.PaymentApprovalRequest;
import com.pintoss.auth.core.payment.application.dto.PaymentApprovalResponse;

public interface PaymentApprover {

    PaymentApprovalResponse approval(PaymentApprovalRequest request);
}
