package com.pintoss.auth.module.order.external;

import com.pintoss.auth.module.order.execution.OrderRepository;
import com.pintoss.auth.module.order.execution.domain.Order;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Optional<Order> findByOrderNo(String orderNo) {
        return orderJpaRepository.findByOrderNo(orderNo);
    }
}
