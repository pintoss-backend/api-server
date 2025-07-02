package com.pintoss.auth.common.event;

import com.pintoss.auth.module.payment.application.PaymentMethodType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherPurchaseEvent {
    private final String orderNo;
    private final Long orderItemId;
    private final String transactionId;
    private final String mId;
    private final Long amount;
    private final PaymentMethodType paymentMethodType;
    private final String productCode;
}
