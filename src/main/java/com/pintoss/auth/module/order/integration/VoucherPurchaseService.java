package com.pintoss.auth.module.order.integration;

import com.pintoss.auth.common.client.billgate.GalaxiaPurchaseResponse;
import com.pintoss.auth.module.payment.application.PaymentMethodType;

public interface VoucherPurchaseService {
    GalaxiaPurchaseResponse purchase(String orderId, String transactionId, String mid, Long taxAmount, PaymentMethodType paymentMethodType);
}
