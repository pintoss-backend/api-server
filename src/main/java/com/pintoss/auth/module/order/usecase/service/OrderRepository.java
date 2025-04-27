package com.pintoss.auth.module.order.usecase.service;

import com.pintoss.auth.module.order.model.Order;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByOrderNo(String orderNo);
}
