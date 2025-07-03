package com.pintoss.auth.core.order.application;

import com.pintoss.auth.core.order.application.flow.OrderReader;
import com.pintoss.auth.core.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderCancelService {

    private final OrderReader orderReader;

    /**
     * 주문 취소
     *
     * @param orderNo 주문 번호
     */
    @Transactional
    public void cancel(String orderNo) {
        Order order = orderReader.getByOrderNo(orderNo);
        order.cancel();
    }
}
