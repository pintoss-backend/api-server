package com.pintoss.auth.module.payment.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseCommand {

    private String serviceId;       // 가맹점 서비스 ID
    private String serviceCode;     // 결제 수단 별 서비스 코드
    private String orderNo;         // 주문 번호
    private String orderDate;       // 주문 일시
    private String transactionId;   // 거래번호
    private String responseCode;    // 응답 코드
    private String message;         // 암호화 메시지 ( 성공 시 전달 )

}
