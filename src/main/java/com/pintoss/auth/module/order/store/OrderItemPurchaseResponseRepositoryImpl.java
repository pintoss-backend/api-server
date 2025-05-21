package com.pintoss.auth.module.order.store;

import com.pintoss.auth.module.order.application.flow.OrderItemPurchaseResponseRepository;
import com.pintoss.auth.module.order.application.model.OrderItemPurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderItemPurchaseResponseRepositoryImpl implements
    OrderItemPurchaseResponseRepository {

    private final OrderItemPurchaseResponseJpaRepository orderItemPurchaseResponseJpaRepository;

    @Override
    public OrderItemPurchaseResponse save(OrderItemPurchaseResponse orderItemPurchaseResponse) {
        return orderItemPurchaseResponseJpaRepository.save(orderItemPurchaseResponse);
    }
}
