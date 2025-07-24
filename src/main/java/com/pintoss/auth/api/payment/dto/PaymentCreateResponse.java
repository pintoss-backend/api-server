package com.pintoss.auth.api.payment.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pintoss.auth.core.payment.application.dto.PaymentCreateResult;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentCreateResponse {
    private String serviceId;
    private String orderNo;
    private Long ordererId;
    private String ordererName;
    private String ordererEmail;
    private String ordererPhone;
    private String serviceCode;
    private Long price;
    private String productName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime orderDate;
    private String checkSum;
    private String checkSumHp;

    private PaymentCreateResponse(String serviceId, String orderNo, Long ordererId, String ordererName, String ordererEmail, String ordererPhone, String serviceCode, Long price, String productName, LocalDateTime orderDate, String checkSum, String checkSumHp) {
        this.serviceId = serviceId;
        this.orderNo = orderNo;
        this.ordererId = ordererId;
        this.ordererName = ordererName;
        this.ordererEmail = ordererEmail;
        this.ordererPhone = ordererPhone;
        this.serviceCode = serviceCode;
        this.price = price;
        this.productName = productName;
        this.orderDate = orderDate;
        this.checkSum = checkSum;
        this.checkSumHp = checkSumHp;
    }

    public static PaymentCreateResponse from(PaymentCreateResult result) {
        return new PaymentCreateResponse(
                result.getServiceId(),
                result.getOrderNo(),
                result.getOrdererId(),
                result.getOrdererName(),
                result.getOrdererEmail(),
                result.getOrdererPhone(),
                result.getServiceCode(),
                result.getPrice(),
                result.getProductName(),
                result.getOrderDate(),
                result.getCheckSum(),
                result.getCheckSumHp()
        );
    }
}
