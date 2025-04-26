package com.pintoss.auth.module.order.execution;

import com.pintoss.auth.module.order.execution.domain.Order;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByOrderNo(String orderNo);
}
