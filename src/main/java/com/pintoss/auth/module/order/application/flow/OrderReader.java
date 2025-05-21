package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.module.order.application.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReader {

    private final OrderRepository orderRepository;

    public Order read(String orderNo){
        return orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ORDER_NOT_FOUND));
    }
}
