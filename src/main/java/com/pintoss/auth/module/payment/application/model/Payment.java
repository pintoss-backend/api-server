package com.pintoss.auth.module.payment.application.model;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.module.order.application.model.PaymentMethodType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Payment {

    private long id;
    private String mid;
    private String serviceId;
    private String serviceCode;
    private String orderNo;
    private LocalDateTime orderDate;
    private String transactionId;
    private String authNumber;
    private long authAmount;
    private LocalDateTime authDate;
    private PaymentStatus status;
    private PaymentMethodType paymentMethodType;

    public Payment(String serviceId, String serviceCode, String orderNo, LocalDateTime orderDate) {
        this.serviceId = serviceId;
        this.serviceCode = serviceCode;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.paymentMethodType = PaymentMethodType.fromServiceCode(serviceCode);
    }
    public void addAuthInfo(String mid, String transactionId, String authNumber, long authAmount, LocalDateTime authDate) {
        this.mid = mid;
        this.transactionId = transactionId;
        this.authNumber = authNumber;
        this.authAmount = authAmount;
        this.authDate = authDate;
        this.status = PaymentStatus.SUCCESS;
    }

    public void fail() {
        this.status = PaymentStatus.FAILED;
    }

    public void success() {
        if (this.status == PaymentStatus.FAILED) {
            throw new BadRequestException(ErrorCode.PAYMENT_ALREADY_CANCELED);
        }
        this.status = PaymentStatus.SUCCESS;
    }

    public void failed() {
        if (this.status == PaymentStatus.FAILED) {
            throw new BadRequestException(ErrorCode.PAYMENT_ALREADY_CANCELED);
        }
        this.status = PaymentStatus.FAILED;
    }
}
