package com.pintoss.auth.support.event;

import com.pintoss.auth.core.payment.application.event.OrderCanceledEventPublisher;
import com.pintoss.auth.core.support.event.OrderCanceledEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringOrderCanceledPublisher implements OrderCanceledEventPublisher {
    private final ApplicationEventPublisher delegate;

    public SpringOrderCanceledPublisher(ApplicationEventPublisher delegate) {
        this.delegate = delegate;
    }

    @Override
    public void publish(OrderCanceledEvent orderCanceledEvent) {
        delegate.publishEvent(orderCanceledEvent);
    }

}
