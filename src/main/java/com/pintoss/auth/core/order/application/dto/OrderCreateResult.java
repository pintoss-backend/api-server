package com.pintoss.auth.core.order.application.dto;

import com.pintoss.auth.core.order.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderCreateResult {

    private String orderNo;
    private String orderName;
    private long totalPrice;
    private LocalDateTime createdAt;
    private OrderStatus status;

    public OrderCreateResult(String orderNo, String orderName, long totalPrice, LocalDateTime createdAt, OrderStatus status) {
        this.orderNo = orderNo;
        this.orderName = orderName;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.status = status;
    }

    public static OrderCreateResult of(String orderNo, String orderName, long totalPrice, LocalDateTime createdAt, OrderStatus status) {
        return new OrderCreateResult(orderNo, orderName, totalPrice, createdAt, status);
    }
}
