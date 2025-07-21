package com.pintoss.auth.core.order.application.flow.viewer;

import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.BadRequestException;
import com.pintoss.auth.core.order.application.repository.OrderRepository;
import com.pintoss.auth.core.order.application.dto.OrderDetail;
import com.pintoss.auth.core.order.application.dto.OrderPageCommand;
import com.pintoss.auth.core.order.application.dto.OrderSearchResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProjectionReader {

    private final OrderRepository orderRepository;

    public OrderProjectionReader(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDetail getDetailById(Long orderId) {
        return orderRepository.findDetailByOrderId(orderId).orElseThrow(
                () -> new BadRequestException(ErrorCode.ORDER_NOT_FOUND)
        );
    }

    public List<OrderSearchResult> searchByUserId(Long userId, OrderPageCommand command) {
        return orderRepository.searchByUserIdAndWithCondition(userId, command);
    }
}
