package com.pintoss.auth.core.payment.application.dto;

import com.pintoss.auth.core.order.domain.Order;
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

    public static PaymentCreateResult from(Order order, PaymentDomain payment, String checkSum, String checkSumHp) {
        return new PaymentCreateResult(
                payment.getServiceId(),
                payment.getOrderNo(),
                order.getOrdererId(),
                order.getOrdererName(),
                order.getOrdererEmail(),
                order.getOrdererPhone(),
                payment.getServiceCode(),
                payment.getAuthAmount(),
                order.getOrderName(),
                payment.getOrderDate(),
                checkSum,
                checkSumHp
        );
    }
}
