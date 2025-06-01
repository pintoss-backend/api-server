package com.pintoss.auth.module.order.store;

import com.pintoss.auth.module.order.domain.OrderItemRefund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRefundJpaRepository extends JpaRepository<OrderItemRefund, Long> {
}
