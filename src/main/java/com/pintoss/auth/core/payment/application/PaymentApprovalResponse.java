package com.pintoss.auth.core.payment.application;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 결제 요청 응답 전문을 파싱해서 저장하는 객체
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentApprovalResponse {

    private Boolean isSuccess;
    private PaymentMethodType paymentMethodType;
    private String transactionId; // 1001
    private String responseCode;  // 1002
    private String responseMessage; // 1003
    private String detailResponseCode; // 1009
    private String detailResponseMessage; // 1010
    private String orderId;
    private LocalDateTime orderDate;
    private Long authAmount;
    private LocalDateTime authDate;

    private String json;
}
