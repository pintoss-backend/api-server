package com.pintoss.auth.module.order.store;

import com.pintoss.auth.module.order.application.model.OrderItemPurchaseResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemPurchaseResponseJpaRepository extends JpaRepository<OrderItemPurchaseResponse, Long> {

}
