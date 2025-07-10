package com.pintoss.auth.api.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pintoss.auth.core.order.application.dto.OrderDetail;
import com.pintoss.auth.core.order.application.dto.OrderItemDetail;
import com.pintoss.auth.core.order.domain.OrderStatus;
import com.pintoss.auth.core.payment.application.PaymentMethodType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailResponse {

    private Long orderId;
    private String orderNo;
    private String paymentMethodType;
    private String orderStatus;
    private Long totalPrice;
    private String ordererName;
    private String ordererPhone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    private List<OrderItemDetailResponse> items;

    public OrderDetailResponse(
            Long orderId,
            String orderNo,
            String paymentMethodType,
            String orderStatus,
            Long totalPrice,
            String ordererName,
            String ordererPhone,
            LocalDateTime orderDate,
            List<OrderItemDetailResponse> items
    ) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.paymentMethodType = paymentMethodType;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.ordererName = ordererName;
        this.ordererPhone = ordererPhone;
        this.orderDate = orderDate;
        this.items = items;
    }

    public static OrderDetailResponse from(OrderDetail detail) {
        return new OrderDetailResponse(
                detail.getOrderId(),
                detail.getOrderNo(),
                detail.getPaymentMethodType() != null ? detail.getPaymentMethodType().name() : null,
                detail.getOrderStatus().name(),
                detail.getTotalPrice(),
                detail.getOrdererName(),
                detail.getOrdererPhone(),
                detail.getOrderDate(),
                detail.getItems().stream()
                        .map(OrderItemDetailResponse::from)
                        .toList()
        );
    }
}
