package com.pintoss.auth.core.payment.application.dto;

import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import lombok.Getter;

@Getter
public class PaymentCreateCommand {

    private String orderNo;
    private PaymentMethodType paymentMethod;

    public PaymentCreateCommand(String orderNo, PaymentMethodType paymentMethod) {
        this.orderNo = orderNo;
        this.paymentMethod = paymentMethod;
    }
}
