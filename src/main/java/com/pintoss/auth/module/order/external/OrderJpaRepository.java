package com.pintoss.auth.module.order.external;

import com.pintoss.auth.module.order.execution.domain.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNo(String orderNo);
}
