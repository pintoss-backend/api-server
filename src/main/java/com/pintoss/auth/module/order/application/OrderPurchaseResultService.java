package com.pintoss.auth.module.order.application;

import com.pintoss.auth.module.order.application.flow.OrderReader;
import com.pintoss.auth.module.order.domain.Order;
import com.pintoss.auth.module.order.domain.OrderItemStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderPurchaseResultService {

    private final OrderReader orderReader;

    public OrderPurchaseResultService(OrderReader orderReader) {
        this.orderReader = orderReader;
    }

    @Transactional
    public void completed(boolean success, String orderNo, Long orderItemId, String pinNo, String approvalCode) {
        Order order = orderReader.getByOrderNo(orderNo);

        order.updateItemStatus(orderItemId, success ? OrderItemStatus.ISSUED : OrderItemStatus.ISSUE_FAILED);

        order.syncOverallStatus();
    }
}
