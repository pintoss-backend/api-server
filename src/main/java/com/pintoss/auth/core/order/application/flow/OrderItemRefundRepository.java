package com.pintoss.auth.core.order.application.flow;

import com.pintoss.auth.core.order.domain.OrderItemRefund;

public interface OrderItemRefundRepository {

    OrderItemRefund save(OrderItemRefund orderItemRefund);
}
