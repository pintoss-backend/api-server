package com.pintoss.auth.support.event;

import com.pintoss.auth.core.support.event.VoucherPurchaseRequestEvent;
import com.pintoss.auth.core.order.application.event.VoucherPurchaseRequestEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringVoucherPurchaseRequestEventPublisher implements
    VoucherPurchaseRequestEventPublisher {

    private final ApplicationEventPublisher delegate;

    public SpringVoucherPurchaseRequestEventPublisher(ApplicationEventPublisher delegate) {
        this.delegate = delegate;
    }

    @Override
    public void publish(VoucherPurchaseRequestEvent voucherPurchaseRequestEvent) {
        delegate.publishEvent(voucherPurchaseRequestEvent);
    }
}
