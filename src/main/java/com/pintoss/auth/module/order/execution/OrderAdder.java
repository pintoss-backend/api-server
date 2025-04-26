package com.pintoss.auth.module.order.execution;

import com.pintoss.auth.module.order.execution.domain.Order;
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
