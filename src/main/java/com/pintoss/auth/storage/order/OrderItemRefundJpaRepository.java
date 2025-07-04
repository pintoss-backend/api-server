package com.pintoss.auth.storage.order;

import com.pintoss.auth.core.order.domain.OrderItemRefund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRefundJpaRepository extends JpaRepository<OrderItemRefund, Long> {
}
