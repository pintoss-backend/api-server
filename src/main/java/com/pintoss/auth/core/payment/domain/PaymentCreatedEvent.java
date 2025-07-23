package com.pintoss.auth.core.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCreatedEvent {

    private final String orderNo;
}
