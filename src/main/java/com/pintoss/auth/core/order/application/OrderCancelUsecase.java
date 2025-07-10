package com.pintoss.auth.core.order.application;

import com.pintoss.auth.core.order.application.flow.validator.OrderValidator;
import com.pintoss.auth.core.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderCancelUsecase {

    private final OrderValidator orderValidator;

    @Transactional
    public void cancel(String orderNo) {
        Order order = orderValidator.getOrThrowIfNotExists(orderNo);
        order.cancel();
    }
}
