package com.pintoss.auth.core.order.application.event;

import com.pintoss.auth.core.support.event.VoucherPurchaseRequestEvent;

public interface VoucherPurchaseRequestEventPublisher {
    void publish(VoucherPurchaseRequestEvent voucherPurchaseRequestEvent);
}
