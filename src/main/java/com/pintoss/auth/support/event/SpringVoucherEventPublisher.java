package com.pintoss.auth.support.event;

import com.pintoss.auth.core.voucher.application.flow.external.VoucherEventPublisher;
import com.pintoss.auth.core.voucher.domain.VoucherPurchaseCompletedEvent;
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
