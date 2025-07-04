package com.pintoss.auth.storage.payment;

import com.pintoss.auth.core.payment.application.PaymentMethodType;
import com.pintoss.auth.core.payment.domain.PaymentDomain;
import com.pintoss.auth.core.payment.domain.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String mid;
    private String serviceCode;
    private String orderNo;
    private LocalDateTime orderDate;
    private String transactionId;
    private long authAmount;
    private LocalDateTime authDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;
    private String detailMessage;
    private String json;

    @Builder
    public PaymentEntity(String mid, String serviceCode, String orderNo, LocalDateTime orderDate, String transactionId, long authAmount, LocalDateTime authDate, PaymentStatus status, PaymentMethodType paymentMethodType, String detailMessage, String json) {
        this.mid = mid;
        this.serviceCode = serviceCode;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.transactionId = transactionId;
        this.authAmount = authAmount;
        this.authDate = authDate;
        this.status = status;
        this.paymentMethodType = paymentMethodType;
        this.detailMessage = detailMessage;
        this.json = json;
    }

    public static PaymentEntity of(PaymentDomain payment) {
        return PaymentEntity.builder()
            .mid(payment.getServiceId())
            .serviceCode(payment.getServiceCode())
            .orderNo(payment.getOrderNo())
            .orderDate(payment.getOrderDate())
            .transactionId(payment.getTransactionId())
            .authAmount(payment.getAuthAmount())
            .authDate(payment.getAuthDate())
            .status(payment.getStatus())
            .paymentMethodType(payment.getPaymentMethodType())
            .detailMessage(payment.getDetailMessage())
            .json(payment.getJson())
            .build();
    }
}
