package com.pintoss.auth.module.order.application;

import com.pintoss.auth.common.event.VoucherPurchaseEvent;
import com.pintoss.auth.module.order.application.flow.OrderReader;
import com.pintoss.auth.module.order.domain.Order;
import com.pintoss.auth.module.order.domain.OrderItem;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderPaymentResultService {

    private final OrderReader orderReader;
    private final ApplicationEventPublisher eventPublisher;

    public OrderPaymentResultService(OrderReader orderReader, ApplicationEventPublisher eventPublisher) {
        this.orderReader = orderReader;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void completed(boolean success, String orderNo, String transactionId, String mId, Long amount, PaymentMethodType paymentMethodType, Long paymentId) {
        Order order = orderReader.getByOrderNo(orderNo);

        order.assignPaymentId(paymentId);

        if (!success) {
            order.markAsPaymentFailed();
            return;
        }

        order.markAsPaid(paymentId);

        for(OrderItem item : order.getOrderItems()) {
            VoucherPurchaseEvent voucherPurchaseEvent = new VoucherPurchaseEvent(
                orderNo,
                item.getId(),
                transactionId,
                mId,
                amount,
                paymentMethodType,
                item.getProductCode()
            );

            eventPublisher.publishEvent(voucherPurchaseEvent);
        }
    }
}
