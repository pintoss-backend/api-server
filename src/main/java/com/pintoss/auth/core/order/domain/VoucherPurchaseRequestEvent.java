package com.pintoss.auth.core.order.domain;

import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherPurchaseRequestEvent {
    private final String orderNo;
    private final Long orderItemId;
    private final String transactionId;
    private final String mId;
    private final Long paymentPrice;
    private final Long salePrice;
    private final PaymentMethodType paymentMethodType;
    private final String productCode;
}
