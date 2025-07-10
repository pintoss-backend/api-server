package com.pintoss.auth.core.order.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pintoss.auth.core.order.domain.OrderStatus;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchResult {
    private Long orderId;
    private String orderNo;
    private OrderStatus status;
    private PaymentMethodType paymentMethodType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    private Long price;
}
