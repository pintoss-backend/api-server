package com.pintoss.auth.module.order.store;

import com.pintoss.auth.module.order.application.flow.OrderItemRefundRepository;
import com.pintoss.auth.module.order.domain.OrderItemRefund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderItemRefundRepositoryImpl implements OrderItemRefundRepository {

    private final OrderItemRefundJpaRepository orderItemRefundJpaRepository;

    @Override
    public OrderItemRefund save(OrderItemRefund orderItemRefund) {
        return orderItemRefundJpaRepository.save(orderItemRefund);
    }
}
