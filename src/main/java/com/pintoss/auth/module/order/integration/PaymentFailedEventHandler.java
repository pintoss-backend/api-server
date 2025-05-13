package com.pintoss.auth.module.order.integration;

import com.pintoss.auth.common.event.PaymentFailedEvent;
import com.pintoss.auth.module.order.application.flow.OrderReader;
import com.pintoss.auth.module.order.application.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentFailedEventHandler {

    private final OrderReader orderReader;
    @EventListener
    public void handlePaymentFailed(PaymentFailedEvent event) {
        // Handle the payment failure logic here
        log.info("Payment failed for order ID: {}", event.getOrderNo());
        Order order = orderReader.read(event.getOrderNo());
        order.cancel();
    }
}
