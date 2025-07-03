package com.pintoss.auth.core.order.application;

import com.pintoss.auth.core.order.application.flow.OrderItemRefundHandler;
import com.pintoss.auth.core.order.application.flow.OrderReader;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.order.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderRefundService {

    private final OrderReader orderReader;
    private final OrderItemRefundHandler orderItemRefundHandler;

    /**
     * 주문 환불
     *
     * @param orderNo 주문 번호
     */
    @Transactional
    public void refund(String orderNo) {
        Order order = orderReader.getByOrderNo(orderNo);

        order.validateRefundable();

        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemRefundHandler.handle(order, orderItem);
        }

        order.markAsRefunded();
    }
}
