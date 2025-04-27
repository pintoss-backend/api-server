package com.pintoss.auth.module.order.usecase;

import com.pintoss.auth.module.order.usecase.service.OrderReader;
import com.pintoss.auth.module.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CancelOrderService {

    private final OrderReader orderReader;

    /**
     * 주문 취소
     *
     * @param orderNo 주문 번호
     */
    @Transactional
    public void cancel(String orderNo) {
        Order order = orderReader.read(orderNo);

        order.cancel();
    }
}
