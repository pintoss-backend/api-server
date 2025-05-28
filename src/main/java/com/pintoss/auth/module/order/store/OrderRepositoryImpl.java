package com.pintoss.auth.module.order.store;

import com.pintoss.auth.module.order.application.model.OrderPageCommand;
import com.pintoss.auth.module.order.application.flow.OrderRepository;
import com.pintoss.auth.module.order.application.model.Order;
import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderSearchResult;
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
        return Optional.empty();
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
