package com.pintoss.auth.support.event;

import com.pintoss.auth.core.support.event.PaymentCompletedEvent;
import com.pintoss.auth.core.payment.application.event.PaymentCompletedEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringPaymentCompletedEventPublisher implements PaymentCompletedEventPublisher {

    private final ApplicationEventPublisher delegate;

    public SpringPaymentCompletedEventPublisher(ApplicationEventPublisher delegate) {
        this.delegate = delegate;
    }

    @Override
    public void publish(PaymentCompletedEvent paymentCompletedEvent) {
        delegate.publishEvent(paymentCompletedEvent);
    }
}
