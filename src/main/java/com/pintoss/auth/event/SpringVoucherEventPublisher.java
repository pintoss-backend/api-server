package com.pintoss.auth.event;

import com.pintoss.auth.support.event.VoucherPurchaseCompletedEvent;
import com.pintoss.auth.core.voucher.application.flow.external.VoucherEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringVoucherEventPublisher implements VoucherEventPublisher {

    private final ApplicationEventPublisher delegate;

    public SpringVoucherEventPublisher(ApplicationEventPublisher delegate) {
        this.delegate = delegate;
    }

    @Override
    public void publish(VoucherPurchaseCompletedEvent voucherPurchaseCompletedEvent) {
        delegate.publishEvent(voucherPurchaseCompletedEvent);
    }
}
