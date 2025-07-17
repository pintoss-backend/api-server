package com.pintoss.auth.support.event;

import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCompletedEvent {
    private final boolean isSuccess;
    private final Long paymentId;
    private final String orderNo;
    private final String transactionId;
    private final String mId;
    private final Long amount;
    private final LocalDateTime completedAt;
    private final PaymentMethodType paymentMethodType;
}
