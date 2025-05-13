package com.pintoss.auth.module.payment.store;

import com.pintoss.auth.module.order.application.model.PaymentMethodType;
import com.pintoss.auth.module.payment.application.model.Payment;
import com.pintoss.auth.module.payment.application.model.PaymentStatus;
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
    private String serviceId;
    private String serviceCode;
    private String orderNo;
    private LocalDateTime orderDate;
    private String transactionId;
    private String authNumber;
    private long authAmount;
    private LocalDateTime authDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;

    @Builder
    public PaymentEntity(String mid, String serviceId, String serviceCode, String orderNo, LocalDateTime orderDate, String transactionId, String authNumber, long authAmount, LocalDateTime authDate, PaymentStatus status, PaymentMethodType paymentMethodType) {
        this.mid = mid;
        this.serviceId = serviceId;
        this.serviceCode = serviceCode;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.transactionId = transactionId;
        this.authNumber = authNumber;
        this.authAmount = authAmount;
        this.authDate = authDate;
        this.status = status;
        this.paymentMethodType = paymentMethodType;
    }

    public static PaymentEntity of(Payment payment) {
        return PaymentEntity.builder()
            .mid(payment.getMid())
            .serviceId(payment.getServiceId())
            .serviceCode(payment.getServiceCode())
            .orderNo(payment.getOrderNo())
            .orderDate(payment.getOrderDate())
            .transactionId(payment.getTransactionId())
            .authNumber(payment.getAuthNumber())
            .authAmount(payment.getAuthAmount())
            .authDate(payment.getAuthDate())
            .status(payment.getStatus())
            .paymentMethodType(payment.getPaymentMethodType())
            .build();
    }
}
