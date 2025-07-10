package com.pintoss.auth.core.payment.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PaymentDomain {
    private long id;
    private String serviceId;
    private String serviceCode;
    private String orderNo;
    private LocalDateTime orderDate;
    private String transactionId;
    private long authAmount;
    private LocalDateTime authDate;
    private PaymentStatus status;
    private PaymentMethodType paymentMethodType;
    private String detailMessage;
    private String json;

    public PaymentDomain(Boolean isSuccess, String serviceId, String serviceCode, String orderNo,
        LocalDateTime orderDate, String transactionId, long authAmount, LocalDateTime authDate,
        PaymentMethodType paymentMethodType, String detailMessage, String json) {
        this.status = isSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
        this.serviceId = serviceId;
        this.serviceCode = serviceCode;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.transactionId = transactionId;
        this.authAmount = authAmount;
        this.authDate = authDate;
        this.paymentMethodType = paymentMethodType;
        this.detailMessage = detailMessage;
        this.json = json;
    }

    public void assignId(Long id) {
        this.id = id;
    }
}
