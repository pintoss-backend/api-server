package com.pintoss.auth.module.order.integration;

public interface VoucherPurchaseService {
    PurchaseResponse purchase(String orderId, String transactionId, String mid, Long taxAmount);
}
