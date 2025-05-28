package com.pintoss.auth.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentFailedEvent {
    private final Long paymentId;
    private final String orderNo;
}
