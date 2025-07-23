package com.pintoss.auth.core.payment.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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

    private PaymentDomain(long id, String serviceId, String serviceCode, String orderNo, LocalDateTime orderDate, String transactionId, long authAmount, LocalDateTime authDate, PaymentStatus status, PaymentMethodType paymentMethodType, String detailMessage, String json) {
        this.id = id;
        this.serviceId = serviceId;
        this.serviceCode = serviceCode;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.transactionId = transactionId;
        this.authAmount = applyTaxPolicy(authAmount, paymentMethodType);
        this.authDate = authDate;
        this.status = status;
        this.paymentMethodType = paymentMethodType;
        this.detailMessage = detailMessage;
        this.json = json;
    }

    public static PaymentDomain create(String serviceId, String orderNo, LocalDateTime orderDate, long price, PaymentStatus status, PaymentMethodType paymentMethodType) {
        return PaymentDomain.builder()
                .serviceId(serviceId)
                .serviceCode(paymentMethodType.getServiceCode())
                .orderNo(orderNo)
                .orderDate(orderDate)
                .authAmount(price)
                .status(status)
                .paymentMethodType(paymentMethodType)
                .build();
    }

    private static long applyTaxPolicy(long basePrice, PaymentMethodType paymentMethodType) {
        if (paymentMethodType == PaymentMethodType.PHONE) {
            return BigDecimal.valueOf(basePrice)
                    .multiply(BigDecimal.valueOf(1.1))
                    .setScale(0, RoundingMode.UP)
                    .longValue();
        }
        return basePrice;
    }

    public void assignId(Long id) {
        this.id = id;
    }
}
