package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.domain.Order;
import com.pintoss.auth.module.order.domain.OrderItem;
import com.pintoss.auth.module.order.domain.OrderItemRefund;
import com.pintoss.auth.module.order.domain.RefundResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemRefundHandler {

    private final VoucherRefundProcessor voucherRefundProcessor;
    private final OrderItemRefundAdder orderItemRefundAdder;
    
    public void handle(Order order, OrderItem item) {
        RefundResult result = voucherRefundProcessor.refund(order.getOrderNo(),
            item.getApprovalCode());

        OrderItemRefund refund = OrderItemRefund.builder()
            .orderNo(order.getOrderNo())
            .orderItemId(item.getId())
            .approvalCode(item.getApprovalCode())
            .message(result.getMessage())
            .status(result.getStatus())
            .build();

        orderItemRefundAdder.add(refund);

        if (result.isSuccess()) {
            item.markAsRefunded();
        } else {
            item.markAsRefundFail();
        }
    }
}
