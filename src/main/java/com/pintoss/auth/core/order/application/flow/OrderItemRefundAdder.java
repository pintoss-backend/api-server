package com.pintoss.auth.core.order.application.flow;

import com.pintoss.auth.core.order.domain.OrderItemRefund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemRefundAdder {
    private final OrderItemRefundRepository orderItemRefundRepository;

    public OrderItemRefund add(OrderItemRefund orderItemRefund) {
        return orderItemRefundRepository.save(orderItemRefund);
    }
}
