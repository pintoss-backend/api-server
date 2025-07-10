package com.pintoss.auth.core.order.application;

import com.pintoss.auth.core.order.application.flow.validator.OrderValidator;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.order.domain.OrderItemStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderPurchaseResultUsecase {

    private final OrderValidator orderValidator;

    public OrderPurchaseResultUsecase(OrderValidator orderValidator) {
        this.orderValidator = orderValidator;
    }

    @Transactional
    public void completed(boolean success, String orderNo, Long orderItemId, String pinNo, String approvalCode) {
        Order order = orderValidator.getOrThrowIfNotExists(orderNo);

        order.updateItemIssueResult(orderItemId, success ? OrderItemStatus.ISSUED : OrderItemStatus.ISSUE_FAILED, pinNo, approvalCode);

        order.syncOverallStatus();
    }
}
