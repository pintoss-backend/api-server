package com.pintoss.auth.support.event;

import com.pintoss.auth.core.support.event.VoucherPurchaseCompletedEvent;
import com.pintoss.auth.core.voucher.application.event.VoucherPurchaseCompletedEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringVoucherPurchaseCompletedEventPublisher implements
    VoucherPurchaseCompletedEventPublisher {

    private final ApplicationEventPublisher delegate;

    public SpringVoucherPurchaseCompletedEventPublisher(ApplicationEventPublisher delegate) {
        this.delegate = delegate;
    }

    @Override
    public void publish(VoucherPurchaseCompletedEvent voucherPurchaseCompletedEvent) {
        delegate.publishEvent(voucherPurchaseCompletedEvent);
    }
}
