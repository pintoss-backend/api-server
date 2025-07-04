package com.pintoss.auth.storage.order;

import com.pintoss.auth.core.order.application.flow.OrderItemRefundRepository;
import com.pintoss.auth.core.order.domain.OrderItemRefund;
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
