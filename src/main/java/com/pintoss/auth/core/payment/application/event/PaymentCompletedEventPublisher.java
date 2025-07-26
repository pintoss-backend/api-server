package com.pintoss.auth.core.payment.application.event;

import com.pintoss.auth.core.support.event.PaymentCompletedEvent;

public interface PaymentCompletedEventPublisher {

    void publish(PaymentCompletedEvent paymentCompletedEvent);
}
