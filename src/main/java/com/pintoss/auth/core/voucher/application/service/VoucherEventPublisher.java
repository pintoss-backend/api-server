package com.pintoss.auth.core.voucher.application.service;

import com.pintoss.auth.common.event.VoucherPurchaseCompletedEvent;

public interface VoucherEventPublisher {
    void publish(VoucherPurchaseCompletedEvent voucherPurchaseCompletedEvent);
}
