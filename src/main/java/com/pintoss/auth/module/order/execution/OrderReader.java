package com.pintoss.auth.module.order.execution;

import com.pintoss.auth.module.order.execution.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReader {

    private final OrderRepository orderRepository;

    public Order read(String orderNo){
        return orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

}
