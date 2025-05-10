package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.module.order.application.model.OrderDetail;
import com.pintoss.auth.module.order.application.model.OrderSummary;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
/*
* Viewer 클래스의 관심사는 UI를 위한 조회에만 집중한다.
* Viewer 클래스의 역할은 단순히 데이터를 조회하는 것이며, 비즈니스 로직을 포함하지 않는다.
* Viewer 클래스의 역할은
* */
@Component
@RequiredArgsConstructor
public class OrderViewer {

    private final OrderQueryRepository orderQueryRepository;

    public OrderDetail getOrderDetail(Long orderId) {
        return orderQueryRepository.getOrderDetail(orderId).orElseThrow(
            () -> new IllegalArgumentException("Order not found with id: " + orderId)
        );
    }

    public List<OrderSummary> getMyOrderList(Long userId) {
        return orderQueryRepository.getMyOrderList(userId);
    }
}
