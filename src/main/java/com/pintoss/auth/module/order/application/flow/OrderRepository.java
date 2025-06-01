package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.domain.OrderPageCommand;
import com.pintoss.auth.module.order.domain.Order;
import com.pintoss.auth.module.order.domain.OrderDetail;
import com.pintoss.auth.module.order.domain.OrderSearchResult;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByOrderNo(String orderNo);

    Optional<OrderDetail> findDetailByOrderId(Long orderId);

    List<OrderSearchResult> searchByUserIdAndWithCondition(Long userId, OrderPageCommand command);

    long countByUserIdWithCondition(Long userId, OrderPageCommand command);
}
