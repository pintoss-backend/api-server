package com.pintoss.auth.core.payment.application.flow.event;

import com.pintoss.auth.support.event.PaymentCompletedEvent;

public interface PaymentEventPublisher {

    void publish(PaymentCompletedEvent paymentCompletedEvent);
}
