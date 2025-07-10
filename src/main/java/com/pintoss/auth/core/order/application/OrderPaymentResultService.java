package com.pintoss.auth.core.order.application;

import com.pintoss.auth.common.event.VoucherPurchaseRequestEvent;
import com.pintoss.auth.core.order.application.flow.OrderReader;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.order.domain.OrderItem;
import com.pintoss.auth.core.payment.application.PaymentMethodType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderPaymentResultService {

    private final OrderReader orderReader;
    private final OrderEventPublisher eventPublisher;

    public OrderPaymentResultService(OrderReader orderReader, OrderEventPublisher eventPublisher) {
        this.orderReader = orderReader;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void completed(boolean success, String orderNo, String transactionId, String mId, Long paymentPrice, PaymentMethodType paymentMethodType, Long paymentId) {
        Order order = orderReader.getByOrderNo(orderNo);

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
}
