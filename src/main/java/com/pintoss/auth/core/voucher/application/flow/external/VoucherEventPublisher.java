package com.pintoss.auth.core.voucher.application.flow.external;

import com.pintoss.auth.support.event.VoucherPurchaseCompletedEvent;

public interface VoucherEventPublisher {
    void publish(VoucherPurchaseCompletedEvent voucherPurchaseCompletedEvent);
}
