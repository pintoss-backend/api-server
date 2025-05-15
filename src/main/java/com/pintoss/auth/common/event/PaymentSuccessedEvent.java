package com.pintoss.auth.common.event;

import com.pintoss.auth.module.order.application.model.PaymentMethodType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentSuccessedEvent {
    private final String orderNo;
    private final String transactionId;
    private final String mId;
    private final Long amount;
    private final LocalDateTime completedAt;
    private final PaymentMethodType paymentMethodType;
}
