package com.pintoss.auth.api.event;


import com.pintoss.auth.common.event.VoucherPurchaseRequestEvent;
import com.pintoss.auth.common.logging.LogContext;
import com.pintoss.auth.core.voucher.application.VoucherPurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VoucherPurchaseRequestEventHandler {

    private final VoucherPurchaseService voucherPurchaseService;

    @EventListener
    public void handle(VoucherPurchaseRequestEvent event) {
        LogContext.putPurchaseRequest(
            event.getOrderNo(),
            event.getOrderItemId(),
            event.getTransactionId(),
            event.getMId(),
            event.getPaymentPrice(),
            event.getSalePrice(),
            event.getPaymentMethodType(),
            event.getProductCode()
        );

        log.info("[상품권 구매 요청]");

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
