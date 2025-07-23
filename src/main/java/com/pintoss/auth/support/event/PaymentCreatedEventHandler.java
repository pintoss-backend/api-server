package com.pintoss.auth.support.event;

import com.pintoss.auth.core.order.application.OrderPaymentResultUsecase;
import com.pintoss.auth.core.payment.domain.PaymentCompletedEvent;
import com.pintoss.auth.support.logging.LogContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentCreatedEventHandler {

    private final OrderPaymentResultUsecase orderPaymentResultUsecase;

    @EventListener
    public void handle(PaymentCompletedEvent event) {

    }
}
