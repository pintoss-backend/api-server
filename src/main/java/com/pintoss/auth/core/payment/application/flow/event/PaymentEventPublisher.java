package com.pintoss.auth.core.payment.application.flow.event;

import com.pintoss.auth.common.event.PaymentCompletedEvent;

public interface PaymentEventPublisher {

    void publish(PaymentCompletedEvent paymentCompletedEvent);
}
