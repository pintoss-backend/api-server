package com.pintoss.auth.support.logging;

import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import org.slf4j.MDC;

public class LogContext {

    private LogContext() {}

    public static void putOrder(String orderNo) {
        MDC.put("domain", "order");
        MDC.put("orderNo", orderNo);
    }

    public static void putPayment(String orderNo, Long paymentId, String transactionId, String mId, PaymentMethodType paymentMethodType) {
        MDC.put("domain", "payment");
        MDC.put("orderNo", orderNo);
        MDC.put("paymentId", String.valueOf(paymentId));
        MDC.put("transactionId", transactionId);
        MDC.put("mId", mId);
        MDC.put("paymentMethodType", paymentMethodType.name());
    }

    public static void putPurchaseResult(String orderNo, Long orderItemId, String pinNo, String approvalCode) {
        MDC.put("domain", "voucher");
        MDC.put("orderNo", orderNo);
        MDC.put("orderItemId", String.valueOf(orderItemId));
        MDC.put("pinNo", pinNo);
        MDC.put("approvalCode", approvalCode);
    }

    public static void clear() {
        MDC.clear();
    }

    public static void putPurchaseRequest(String orderNo, Long orderItemId, String transactionId, String mId, Long paymentPrice, Long salePrice, PaymentMethodType paymentMethodType, String productCode) {
        MDC.put("domain", "voucher");
        MDC.put("orderNo", orderNo);
        MDC.put("orderItemId", String.valueOf(orderItemId));
        MDC.put("transactionId", transactionId);
        MDC.put("mId", mId);
        MDC.put("paymentPrice", String.valueOf(paymentPrice));
        MDC.put("salePrice", String.valueOf(salePrice));
        MDC.put("paymentMethodType", paymentMethodType.name());
        MDC.put("productCode", productCode);
    }
}
