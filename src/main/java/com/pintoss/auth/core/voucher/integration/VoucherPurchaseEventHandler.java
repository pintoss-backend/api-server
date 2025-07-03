package com.pintoss.auth.core.voucher.integration;


import com.pintoss.auth.common.event.VoucherPurchaseEvent;
import com.pintoss.auth.core.voucher.usecase.VoucherPurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VoucherPurchaseEventHandler {

    private final VoucherPurchaseService voucherPurchaseService;

    @EventListener
    public void handle(VoucherPurchaseEvent event) {
        log.info("[상품권 구매 요청] 주문번호: {}, 주문상품ID: {}, 트랜잭션ID: {}, mId: {}, 결제 가격: {}, 상품 가격: {}, 결제수단: {}, 상품번호: {}",
            event.getOrderNo(), event.getOrderItemId(), event.getTransactionId(), event.getMId(), event.getPaymentPrice(), event.getSalePrice(), event.getPaymentMethodType(), event.getProductCode());

        voucherPurchaseService.purchase(
            event.getOrderNo(),
            event.getOrderItemId(),
            event.getTransactionId(),
            event.getMId(),
            event.getPaymentPrice(),
            event.getSalePrice(),
            event.getPaymentMethodType(),
            event.getProductCode()
        );
    }
}
