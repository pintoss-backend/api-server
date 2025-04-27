package com.pintoss.auth.module.order.usecase.service;

import com.pintoss.auth.module.order.model.Order;
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
