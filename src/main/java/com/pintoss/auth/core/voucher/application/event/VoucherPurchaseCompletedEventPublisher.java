package com.pintoss.auth.core.voucher.application.event;

import com.pintoss.auth.core.support.event.VoucherPurchaseCompletedEvent;

public interface VoucherPurchaseCompletedEventPublisher {
    void publish(VoucherPurchaseCompletedEvent voucherPurchaseCompletedEvent);
}
