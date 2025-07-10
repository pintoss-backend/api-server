package com.pintoss.auth.core.order.application.repository;

import com.pintoss.auth.core.order.application.dto.OrderPageCommand;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.order.application.dto.OrderDetail;
import com.pintoss.auth.core.order.application.dto.OrderSearchResult;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByOrderNo(String orderNo);

    Optional<OrderDetail> findDetailByOrderId(Long orderId);

    List<OrderSearchResult> searchByUserIdAndWithCondition(Long userId, OrderPageCommand command);

    long countByUserIdWithCondition(Long userId, OrderPageCommand command);
}
