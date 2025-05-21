package com.pintoss.auth.module.order.integration;


import com.pintoss.auth.common.event.VoucherPurchaseEvent;
import com.pintoss.auth.module.order.application.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VoucherPurchaseEventHandler {

    private final OrderService orderService;

    @EventListener
    public void handle(VoucherPurchaseEvent event) {
        // Handle the voucher purchase event here
        orderService.assignPinToVoucher(
            event.getOrderNo(),
            event.getTransactionId(),
            event.getMId(),
            event.getPaymentMethodType()
        );
        log.info("[Voucher Purchase Event] orderNo: {}, transactionId: {}, mId: {}, amount: {}, paymentMethodType: {}",
            event.getOrderNo(), event.getTransactionId(), event.getMId(), event.getAmount(), event.getPaymentMethodType());
    }
}
