package com.pintoss.auth.module.order.integration;

import com.pintoss.auth.common.event.PaymentSuccessedEvent;
import com.pintoss.auth.common.event.VoucherPurchaseEvent;
import com.pintoss.auth.module.order.application.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentSuccessEventHandler {

    private final OrderService orderService;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void handlePaymentCompleted(PaymentSuccessedEvent event) {
        orderService.markAsPaid(event.getOrderNo(), event.getPaymentId());

        VoucherPurchaseEvent voucherPurchaseEvent = new VoucherPurchaseEvent(
            event.getOrderNo(),
            event.getTransactionId(),
            event.getMId(),
            event.getAmount(),
            event.getPaymentMethodType()
        );
        eventPublisher.publishEvent(voucherPurchaseEvent);
        log.info("[주문 결제 완료] orderNo: {}, transactionId: {}, mId: {}, amount: {}, paymentMethodType: {}",
            event.getOrderNo(), event.getTransactionId(), event.getMId(), event.getAmount(), event.getPaymentMethodType());
    }
}
