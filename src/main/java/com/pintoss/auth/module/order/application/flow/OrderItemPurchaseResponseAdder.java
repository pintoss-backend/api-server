package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.application.model.OrderItemPurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemPurchaseResponseAdder {
    private final OrderItemPurchaseResponseRepository orderItemPurchaseResponse;

    public OrderItemPurchaseResponse add(OrderItemPurchaseResponse orderItemPurchaseResponse) {
        return this.orderItemPurchaseResponse.save(orderItemPurchaseResponse);
    }
}
