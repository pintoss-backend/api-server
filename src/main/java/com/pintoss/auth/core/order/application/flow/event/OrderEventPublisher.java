package com.pintoss.auth.core.order.application.flow.event;

import com.pintoss.auth.core.order.domain.VoucherPurchaseRequestEvent;

public interface OrderEventPublisher {
    void publish(VoucherPurchaseRequestEvent voucherPurchaseRequestEvent);
}
