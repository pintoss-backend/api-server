package com.pintoss.auth.core.payment.integration;

import com.pintoss.auth.core.payment.application.PurchaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCallbackRequest {
    private String SERVICE_ID;                 // 가맹점 서비스아이디 (최대 20자)
    private String SERVICE_CODE;              // 결제 수단 별 서비스 코드 (4자)
    private String ORDER_ID;                  // 주문번호 (64자)
    private String ORDER_DATE;                // 주문일시 (14자, YYYYMMDDHH24MISS)
    private String TRANSACTION_ID;            // 거래번호 (20자, 빌게이트 전용)
    private String RESPONSE_CODE;             // 응답코드 (4자)
    private String RESPONSE_MESSAGE;          // 응답 메시지 (64자)
    private String DETAIL_RESPONSE_CODE;      // 상세 응답 코드 (2자)
    private String DETAIL_RESPONSE_MESSAGE;   // 상세 응답 메시지 (64자)
    private String MESSAGE;                   // 암호화 메시지 (성공 시 전달)
    private String RESERVED1;                 // 예비 필드 1
    private String RESERVED2;                 // 예비 필드 2
    private String RESERVED3;                 // 예비 필드 3
    private String CERT_TYPE;                 // (신용카드 응답 추가) 네이버페이 인증구분 (4자)

    public PurchaseCommand toPurchaseCommand() {
        return PurchaseCommand.builder()
            .serviceId(SERVICE_ID)
            .serviceCode(SERVICE_CODE)
            .orderNo(ORDER_ID)
            .orderDate(ORDER_DATE)
            .transactionId(TRANSACTION_ID)
            .responseCode(RESPONSE_CODE)
            .message(MESSAGE)
            .build();
    }
}
