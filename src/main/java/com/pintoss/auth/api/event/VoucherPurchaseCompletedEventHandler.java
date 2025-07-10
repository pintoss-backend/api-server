package com.pintoss.auth.api.event;

import com.pintoss.auth.common.event.VoucherPurchaseCompletedEvent;
import com.pintoss.auth.common.logging.LogContext;
import com.pintoss.auth.core.order.application.OrderPurchaseResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VoucherPurchaseCompletedEventHandler {

    private final OrderPurchaseResultService orderPurchaseResultService;

    @EventListener
    public void handle(VoucherPurchaseCompletedEvent event) {
        LogContext.putPurchaseResult(
            event.getOrderNo(),
            event.getOrderItemId(),
            event.getPinNo(),
            event.getApprovalCode()
        );

        if (event.isSuccess()) {
            log.info("[상품권 구매 성공]");
        } else {
            log.info("[상품권 구매 실패]");
        }

        orderPurchaseResultService.completed(
            event.isSuccess(),
            event.getOrderNo(),
            event.getOrderItemId(),
            event.getPinNo(),
            event.getApprovalCode()
        );

    }
}
