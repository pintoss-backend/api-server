package com.pintoss.auth.core.payment.application;

import com.pintoss.auth.common.event.PaymentCompletedEvent;

public interface PaymentEventPublisher {

    void publish(PaymentCompletedEvent paymentCompletedEvent);
}
