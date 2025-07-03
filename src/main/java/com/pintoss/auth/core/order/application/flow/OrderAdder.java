package com.pintoss.auth.core.order.application.flow;

import com.pintoss.auth.core.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAdder {

    private final OrderRepository orderRepository;

    public Order add(Order order) {
        return orderRepository.save(order);
    }

}
