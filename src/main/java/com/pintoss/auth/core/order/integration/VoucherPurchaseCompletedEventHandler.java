package com.pintoss.auth.core.order.integration;

import com.pintoss.auth.common.event.VoucherPurchaseCompletedEvent;
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
        if (event.isSuccess()) {
            log.info("[상품권 구매 성공] 주문번호: {}, 주문상품ID: {}, 핀번호: {}, 승인번호: {}",
                event.getOrderNo(), event.getOrderItemId(), event.getPinNo(), event.getApprovalCode());
        } else {
            log.info("[상품권 구매 실패] 주문번호: {}, 주문상품ID: {}, 핀번호: {}, 승인번호: {}",
                event.getOrderNo(), event.getOrderItemId(), event.getPinNo(), event.getApprovalCode());
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
