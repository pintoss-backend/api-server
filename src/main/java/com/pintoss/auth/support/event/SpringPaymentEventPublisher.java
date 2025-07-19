package com.pintoss.auth.support.event;

import com.pintoss.auth.core.payment.domain.PaymentCompletedEvent;
import com.pintoss.auth.core.payment.application.flow.event.PaymentEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringPaymentEventPublisher implements PaymentEventPublisher {

    private final ApplicationEventPublisher delegate;

    public SpringPaymentEventPublisher(ApplicationEventPublisher delegate) {
        this.delegate = delegate;
    }

    @Override
    public void publish(PaymentCompletedEvent paymentCompletedEvent) {
        delegate.publishEvent(paymentCompletedEvent);
    }
}
