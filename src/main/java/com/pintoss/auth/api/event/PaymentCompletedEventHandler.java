package com.pintoss.auth.api.event;

import com.pintoss.auth.common.event.PaymentCompletedEvent;
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
        if (event.isSuccess()) {
            log.info("[결제 승인] 주문번호: {}, 결제ID: {}, 트랜잭션ID: {}, mId: {}, 승인금액: {}, 결제수단: {}",
                event.getOrderNo(), event.getPaymentId(), event.getTransactionId(), event.getMId(), event.getAmount(), event.getPaymentMethodType());
        } else {
            log.error("[결제 실패] 주문번호: {}, 결제ID: {}, 트랜잭션ID: {}, mId: {}, 승인금액: {}, 결제수단: {}",
                event.getOrderNo(), event.getPaymentId(), event.getTransactionId(), event.getMId(), event.getAmount(), event.getPaymentMethodType());
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
