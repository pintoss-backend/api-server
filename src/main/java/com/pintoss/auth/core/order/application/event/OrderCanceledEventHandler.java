package com.pintoss.auth.core.order.application.event;

import com.pintoss.auth.core.order.application.OrderPaymentResultUsecase;
import com.pintoss.auth.core.support.event.OrderCanceledEvent;
import com.pintoss.auth.support.logging.LogContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCanceledEventHandler {

    private final OrderPaymentResultUsecase orderPaymentResultUsecase;

    @EventListener
    public void handle(OrderCanceledEvent event) {
        LogContext.putOrder(event.getOrderNo());
        log.info("[주문 취소]");
        orderPaymentResultUsecase.canceled(event.getOrderNo());
    }

}
