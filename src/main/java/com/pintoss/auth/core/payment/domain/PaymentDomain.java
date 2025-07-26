package com.pintoss.auth.core.payment.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PaymentDomain {
    private Long id;
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

    public static PaymentDomain create(Long id, String mid, String serviceCode, String orderNo, LocalDateTime orderDate, String transactionId, long authAmount,
        LocalDateTime authDate, PaymentStatus status, PaymentMethodType paymentMethodType, String detailMessage, String json) {
        return PaymentDomain.builder()
                .id(id)
                .serviceId(mid)
                .serviceCode(serviceCode)
                .orderNo(orderNo)
                .orderDate(orderDate)
                .transactionId(transactionId)
                .authAmount(applyTaxPolicy(authAmount, paymentMethodType))
                .authDate(authDate)
                .status(status)
                .paymentMethodType(paymentMethodType)
                .detailMessage(detailMessage)
                .json(json)
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

    public void approve(Boolean isSuccess, String transactionId, LocalDateTime authDate, String detailResponseCode, String json) {
        this.status = isSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
        this.transactionId = transactionId;
        this.authDate = authDate;
        this.detailMessage = detailResponseCode;
        this.json = json;
    }

    public void cancel() {
        this.status = PaymentStatus.CANCELED;
    }
}
