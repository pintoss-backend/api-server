package com.pintoss.auth.module.order.application;

import com.pintoss.auth.module.order.application.flow.OrderReader;
import com.pintoss.auth.module.order.application.model.Order;
import com.pintoss.auth.module.order.application.model.OrderItem;
import com.pintoss.auth.module.order.integration.VoucherCancelClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderCancelService {

    private final OrderReader orderReader;
    private final VoucherCancelClient voucherCancelClient;

    /**
     * 주문 취소
     *
     * @param orderNo 주문 번호
     */
    @Transactional
    public void cancel(String orderNo) {
        Order order = orderReader.getByOrderNo(orderNo);

        for (OrderItem orderItem : order.getOrderItems()) {
            voucherCancelClient.refund(order.getOrderNo(),
                orderItem.getApprovalCode());
        }
        order.cancel();
    }
}
