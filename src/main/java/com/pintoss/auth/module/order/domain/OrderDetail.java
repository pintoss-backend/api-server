package com.pintoss.auth.module.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private Long orderId;
    private String orderNo;
    private PaymentMethodType paymentMethodType;
    private OrderStatus orderStatus;
    private Long totalPrice;
    private String ordererName;
    private String ordererPhone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    private List<OrderItemDetail> items;
}
