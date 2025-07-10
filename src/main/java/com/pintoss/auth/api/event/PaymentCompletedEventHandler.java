package com.pintoss.auth.api.event;

import com.pintoss.auth.common.event.PaymentCompletedEvent;
import com.pintoss.auth.common.logging.LogContext;
import com.pintoss.auth.core.order.application.OrderPaymentResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentCompletedEventHandler {

    private final OrderPaymentResultService orderPaymentResultService;

    @EventListener
    public void handle(PaymentCompletedEvent event) {
        LogContext.putPayment(
                event.getOrderNo(),
                event.getPaymentId(),
                event.getTransactionId(),
                event.getMId(),
                event.getPaymentMethodType()
        );
        if (event.isSuccess()) {
            log.info("[결제 승인]");
        } else {
            log.error("[결제 실패]");
        }
        orderPaymentResultService.completed(
            event.isSuccess(),
            event.getOrderNo(),
            event.getTransactionId(),
            event.getMId(),
            event.getAmount(),
            event.getPaymentMethodType(),
            event.getPaymentId()
        );
    }
}
