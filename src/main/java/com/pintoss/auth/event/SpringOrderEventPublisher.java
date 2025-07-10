package com.pintoss.auth.event;

import com.pintoss.auth.common.event.VoucherPurchaseRequestEvent;
import com.pintoss.auth.core.order.application.OrderEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringOrderEventPublisher implements OrderEventPublisher {

    private final ApplicationEventPublisher delegate;

    public SpringOrderEventPublisher(ApplicationEventPublisher delegate) {
        this.delegate = delegate;
    }

    @Override
    public void publish(VoucherPurchaseRequestEvent voucherPurchaseRequestEvent) {
        delegate.publishEvent(voucherPurchaseRequestEvent);
    }
}
