package com.pintoss.auth.common.event;

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
}
