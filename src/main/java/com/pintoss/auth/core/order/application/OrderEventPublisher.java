package com.pintoss.auth.core.order.application;

import com.pintoss.auth.common.event.VoucherPurchaseRequestEvent;

public interface OrderEventPublisher {
    void publish(VoucherPurchaseRequestEvent voucherPurchaseRequestEvent);
}
