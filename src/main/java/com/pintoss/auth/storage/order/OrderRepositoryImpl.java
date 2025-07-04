package com.pintoss.auth.storage.order;

import com.pintoss.auth.core.order.domain.OrderPageCommand;
import com.pintoss.auth.core.order.application.flow.OrderRepository;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.order.domain.OrderDetail;
import com.pintoss.auth.core.order.domain.OrderSearchResult;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderQueryDslRepository orderQueryDslRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Optional<Order> findByOrderNo(String orderNo) {
        return orderJpaRepository.findByOrderNo(orderNo);
    }

    @Override
    public Optional<OrderDetail> findDetailByOrderId(Long orderId) {
        return orderQueryDslRepository.findDetailByOrderId(orderId);
    }

    @Override
    public List<OrderSearchResult> searchByUserIdAndWithCondition(Long userId,
        OrderPageCommand command) {
        return orderQueryDslRepository.searchByUserIdAndWithCondition(userId, command);
    }

    @Override
    public long countByUserIdWithCondition(Long userId, OrderPageCommand command) {
        return orderQueryDslRepository.countByUserIdWithCondition(userId, command);
    }
}
