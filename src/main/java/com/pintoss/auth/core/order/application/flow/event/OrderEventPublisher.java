package com.pintoss.auth.core.order.application.flow.event;

import com.pintoss.auth.support.event.VoucherPurchaseRequestEvent;

public interface OrderEventPublisher {
    void publish(VoucherPurchaseRequestEvent voucherPurchaseRequestEvent);
}
