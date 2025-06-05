package com.pintoss.auth.api.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pintoss.auth.module.order.domain.OrderItemDetail;
import com.pintoss.auth.module.order.domain.OrderStatus;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailResponse {

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
