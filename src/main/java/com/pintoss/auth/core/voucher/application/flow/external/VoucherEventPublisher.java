package com.pintoss.auth.core.voucher.application.flow.external;

import com.pintoss.auth.common.event.VoucherPurchaseCompletedEvent;

public interface VoucherEventPublisher {
    void publish(VoucherPurchaseCompletedEvent voucherPurchaseCompletedEvent);
}
