package com.pintoss.auth.module.order.store;

import com.pintoss.auth.module.order.application.flow.OrderQueryRepository;
import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderSummary;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final OrderQueryDslRepository orderQueryDslRepository;

    @Override
    public Optional<OrderDetail> getOrderDetail(Long orderId) {
        return orderQueryDslRepository.getOrderDetail(orderId);
    }

    @Override
    public List<OrderSummary> getMyOrderList(Long userId) {
        return orderQueryDslRepository.getMyOrderList(userId);
    }
}
