package com.pintoss.auth.core.voucher.application.flow.external;

import com.pintoss.auth.core.voucher.domain.VoucherPurchaseCompletedEvent;

public interface VoucherEventPublisher {
    void publish(VoucherPurchaseCompletedEvent voucherPurchaseCompletedEvent);
}
