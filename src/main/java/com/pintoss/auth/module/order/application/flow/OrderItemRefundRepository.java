package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.domain.OrderItemRefund;

public interface OrderItemRefundRepository {

    OrderItemRefund save(OrderItemRefund orderItemRefund);
}
