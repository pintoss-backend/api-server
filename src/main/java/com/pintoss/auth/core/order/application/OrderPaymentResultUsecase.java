package com.pintoss.auth.core.order.application;

import com.pintoss.auth.core.support.event.VoucherPurchaseRequestEvent;
import com.pintoss.auth.core.order.application.flow.validator.OrderValidator;
import com.pintoss.auth.core.order.application.event.VoucherPurchaseRequestEventPublisher;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.order.domain.OrderItem;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderPaymentResultUsecase {

    private final OrderValidator orderValidator;
    private final VoucherPurchaseRequestEventPublisher eventPublisher;

    public OrderPaymentResultUsecase(OrderValidator orderValidator, VoucherPurchaseRequestEventPublisher eventPublisher) {
        this.orderValidator = orderValidator;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void completed(boolean success, String orderNo, String transactionId, String mId, Long paymentPrice, PaymentMethodType paymentMethodType, Long paymentId) {
        Order order = orderValidator.getOrThrowIfNotExists(orderNo);

        order.assignPaymentId(paymentId);

        if (!success) {
            order.markAsPaymentFailed();
            return;
        }

        order.markAsPaid(paymentId);

        for(OrderItem item : order.getOrderItems()) {
            VoucherPurchaseRequestEvent voucherPurchaseRequestEvent = new VoucherPurchaseRequestEvent(
                orderNo,
                item.getId(),
                transactionId,
                mId,
                paymentPrice,
                item.getPrice(),
                paymentMethodType,
                item.getProductCode()
            );

            eventPublisher.publish(voucherPurchaseRequestEvent);
        }
    }

    @Transactional
    public void canceled(String orderNo) {
        Order order = orderValidator.getOrThrowIfNotExists(orderNo);
        order.cancel();
    }
}
