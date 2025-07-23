package com.pintoss.auth.core.payment.application.dto;

import com.pintoss.auth.core.payment.domain.PaymentDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PaymentCreateResult {
    private String serviceId;
    private String orderNo;
    private Long ordererId;
    private String ordererName;
    private String ordererEmail;
    private String ordererPhone;
    private String serviceCode;
    private Long price;
    private String productName;
    private LocalDateTime orderDate;
    private String checkSum;
    private String checkSumHp;


    public static PaymentCreateResult from(PaymentDomain payment) {
        return new PaymentCreateResult(
                payment.getServiceId(),
                payment.getOrderNo(),
                null, // ordererId is not available in PaymentDomain
                null, // ordererName is not available in PaymentDomain
                null, // ordererEmail is not available in PaymentDomain
                null, // ordererPhone is not available in PaymentDomain
                payment.getServiceCode(),
                payment.getAuthAmount(),
                null, // productName is not available in PaymentDomain
                payment.getOrderDate(),
                null, // checkSum is not available in PaymentDomain
                null  // checkSumHp is not available in PaymentDomain
        );
    }
}
