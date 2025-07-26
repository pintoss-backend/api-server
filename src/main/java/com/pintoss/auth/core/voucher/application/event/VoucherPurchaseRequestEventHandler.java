package com.pintoss.auth.core.voucher.application.event;


import com.pintoss.auth.core.support.event.VoucherPurchaseRequestEvent;
import com.pintoss.auth.support.logging.LogContext;
import com.pintoss.auth.core.voucher.application.VoucherPurchaseUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VoucherPurchaseRequestEventHandler {

    private final VoucherPurchaseUsecase voucherPurchaseUsecase;

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

        voucherPurchaseUsecase.purchase(event.toCommand());
    }
}
