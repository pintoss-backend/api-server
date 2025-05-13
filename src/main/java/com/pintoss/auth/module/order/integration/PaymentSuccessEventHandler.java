package com.pintoss.auth.module.order.integration;

import com.pintoss.auth.common.event.PaymentSuccessedEvent;
import com.pintoss.auth.module.order.application.flow.OrderReader;
import com.pintoss.auth.module.order.application.model.Order;
import com.pintoss.auth.module.order.application.model.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentSuccessEventHandler {

    private final OrderReader orderReader;
    private final VoucherPurchaseService voucherPurchaseService;

    @EventListener
    public void handlePaymentCompleted(PaymentSuccessedEvent event) {
        // Handle the payment completion logic here
        log.info("Payment completed for order ID: {} at {}", event.getOrderNo(), event.getCompletedAt());
        Order order = orderReader.read(event.getOrderNo());
        order.paymentSuccess();

        for(OrderItem orderItem: order.getOrderItems()) {
            PurchaseResponse purchase = voucherPurchaseService.purchase(order.getOrderNo(),
                event.getTransactionId(), event.getMId(), event.getAmount());
            orderItem.assignPinNum(purchase.getCardNo());
            orderItem.issued();
        }
        order.issued();
    }
}
