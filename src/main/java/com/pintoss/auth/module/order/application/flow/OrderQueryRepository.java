package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderSummary;
import java.util.List;
import java.util.Optional;

public interface OrderQueryRepository {

    Optional<OrderDetail> getOrderDetail(Long orderId);

    List<OrderSummary> getMyOrderList(Long userId);
}
