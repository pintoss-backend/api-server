package com.pintoss.auth.support.event;

import com.pintoss.auth.core.order.domain.VoucherPurchaseRequestEvent;
import com.pintoss.auth.core.order.application.flow.event.OrderEventPublisher;
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
