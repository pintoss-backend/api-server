package com.pintoss.auth.core.voucher.application.flow.external;

import com.pintoss.auth.core.order.domain.PurchaseResult;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;

public interface Purchaser {
    PurchaseResult purchase(String orderNo, String transactionId, String mid, Long paymentPrice, PaymentMethodType paymentMethodType, Long salePrice, String productCode);
}
