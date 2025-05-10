package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.application.model.Order;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByOrderNo(String orderNo);
}
