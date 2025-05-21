package com.pintoss.auth.module.order.application.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {
    private Long orderId;
    private String orderNo;
    private OrderStatus status;
    private PaymentMethodType paymentMethodType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    private Long price;
}
