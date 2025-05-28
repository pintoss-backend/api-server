package com.pintoss.auth.module.order.integration;

import com.pintoss.auth.common.event.PaymentFailedEvent;
import com.pintoss.auth.module.order.application.flow.OrderReader;
import com.pintoss.auth.module.order.application.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentFailedEventHandler {

    private final OrderReader orderReader;
    @EventListener
    @Transactional
    public void handlePaymentFailed(PaymentFailedEvent event) {
        // Handle the payment failure logic here
        log.info("Payment failed for order ID: {}", event.getOrderNo());
        Order order = orderReader.getByOrderNo(event.getOrderNo());
        order.assignPaymentId(event.getPaymentId());
        order.cancel();
    }
}
