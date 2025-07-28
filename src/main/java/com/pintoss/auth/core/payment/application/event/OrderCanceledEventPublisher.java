package com.pintoss.auth.core.payment.application.event;

import com.pintoss.auth.core.support.event.OrderCanceledEvent;

public interface OrderCanceledEventPublisher {

    void publish(OrderCanceledEvent orderCanceledEvent);

}
