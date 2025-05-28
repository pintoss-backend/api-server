package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.application.model.OrderPageCommand;
import com.pintoss.auth.module.order.application.model.Order;
import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderSearchResult;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByOrderNo(String orderNo);

    Optional<OrderDetail> findDetailByOrderId(Long orderId);

    List<OrderSearchResult> searchByUserIdAndWithCondition(Long userId, OrderPageCommand command);

    long countByUserIdWithCondition(Long userId, OrderPageCommand command);
}
