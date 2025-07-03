package com.pintoss.auth.core.order.application.flow;

import com.pintoss.auth.core.order.domain.OrderPageCommand;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.order.domain.OrderDetail;
import com.pintoss.auth.core.order.domain.OrderSearchResult;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByOrderNo(String orderNo);

    Optional<OrderDetail> findDetailByOrderId(Long orderId);

    List<OrderSearchResult> searchByUserIdAndWithCondition(Long userId, OrderPageCommand command);

    long countByUserIdWithCondition(Long userId, OrderPageCommand command);
}
