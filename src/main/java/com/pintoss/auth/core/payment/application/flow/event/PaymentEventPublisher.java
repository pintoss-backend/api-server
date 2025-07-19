package com.pintoss.auth.core.payment.application.flow.event;

import com.pintoss.auth.core.payment.domain.PaymentCompletedEvent;

public interface PaymentEventPublisher {

    void publish(PaymentCompletedEvent paymentCompletedEvent);
}
