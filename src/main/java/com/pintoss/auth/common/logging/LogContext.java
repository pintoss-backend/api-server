package com.pintoss.auth.common.logging;

import org.slf4j.MDC;

public class LogContext {

    private LogContext() {}
    // TODO : 비동기 이벤트 발생 시 MDC 이전하는 코드 
    public static void putOrder(String orderNo) {
        MDC.put("domain", "order");
        MDC.put("orderNo", orderNo);
    }

    public static void putPayment(String paymentId) {
        MDC.put("domain", "payment");
        MDC.put("paymentId", paymentId);
    }

    public static void putVoucher(String voucherId) {
        MDC.put("domain", "voucher");
        MDC.put("voucherId", voucherId);
    }

    public static void clear() {
        MDC.clear();
    }
}
