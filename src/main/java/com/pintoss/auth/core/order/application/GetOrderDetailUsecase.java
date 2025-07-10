package com.pintoss.auth.core.order.application;

import com.pintoss.auth.core.order.application.flow.viewer.OrderProjectionReader;
import com.pintoss.auth.core.order.application.dto.OrderDetail;
import org.springframework.stereotype.Component;


@Component
public class GetOrderDetailUsecase {

    private final OrderProjectionReader orderProjectionReader;

    public GetOrderDetailUsecase(OrderProjectionReader orderProjectionReader) {
        this.orderProjectionReader = orderProjectionReader;
    }

    public OrderDetail getOrderDetail(Long orderId) {
        return orderProjectionReader.getDetailById(orderId);
    }
}
